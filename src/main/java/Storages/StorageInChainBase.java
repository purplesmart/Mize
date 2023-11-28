package Storages;

import Interfaces.IStorage;

import java.io.IOException;

public abstract class StorageInChainBase<T> implements IStorage<T>,Cloneable {

    private final int expirationInterval;
    private IStorage<T> nextStorage;
    private T value;

    protected boolean isReadOnly = false;

    public int getExpirationInterval() {
        return expirationInterval;
    }


    @Override
    public boolean IsReadOnly() {
        return isReadOnly;
    }


    @Override
    public void Propagate(String key, T value) throws IOException {
        if (!isReadOnly) {
            this.addValue(key, value);
        }
        if (this.getNextStorage() != null)
            this.getNextStorage().Propagate(key, value);
    }

    public StorageInChainBase(int depth,int expirationInterval) {
        this.expirationInterval = expirationInterval;
        IStorage<T> nextStorage = this;
        for(int i = 0; i < depth - 1; i++){
            IStorage<T> storageWrapper = new StorageWrapper<T>(((StorageInChainBase<T>)nextStorage).clone());
            storageWrapper = ((StorageWrapper<T>) storageWrapper).storage;
            nextStorage.setNextStorage(storageWrapper);
            nextStorage = storageWrapper;
        }
        ((StorageInChainBase<T>)nextStorage).isReadOnly = true;
    }

    @Override
    public IStorage<T> getNextStorage() {
        return nextStorage;
    }

    @Override
    public void setNextStorage(IStorage<T> nextStorage) {
        this.nextStorage = nextStorage;
    }

    public void addValue(String key, T value, int depth) throws IllegalAccessException, IOException {
        IStorage<T> currentStorage = this;
        int counter = 1;

        while (depth != counter) {
            if (currentStorage.getNextStorage() == null) {
                throw new IllegalAccessException("Depth to deep for the chain");
            }
            currentStorage = currentStorage.getNextStorage();
            counter++;
        }
        if (!isReadOnly) {
            currentStorage.addValue(key, value);
        }
    }

    @Override
    public StorageInChainBase<T> clone() {
        try {
            StorageInChainBase clone = (StorageInChainBase) super.clone();
            clone.Init();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
