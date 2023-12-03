package Storages;

import Interfaces.IStorage;

import java.io.IOException;

public class StorageWrapper<T> implements IStorage<T> {

    protected IStorage<T> storage;

    public StorageWrapper(IStorage<T> storage) {
        this.storage = storage;
    }

    @Override
    public T getValue(String key) throws Exception {
        return storage.getValue(key);
    }

    @Override
    public IStorage<T> getNextStorage() {
        return storage.getNextStorage();
    }

    @Override
    public void setNextStorage(IStorage<T> nextStorage) {
        storage.setNextStorage(nextStorage);
    }

    @Override
    public void addValue(String key, T value) throws IOException, IllegalAccessException {
        if (!IsReadOnly()) {
            storage.addValue(key, value);
        }
    }

    @Override
    public void addValue(String key, T value, int depth) throws IllegalAccessException, IOException {
        storage.addValue(key, value, depth);
    }

    @Override
    public boolean IsReadOnly() {
        return storage.IsReadOnly();
    }

    @Override
    public void Init() {
        storage.Init();
    }

    @Override
    public void Propagate(String key, T value) throws Exception {
        storage.Propagate(key, value);
    }

    @Override
    public boolean ValueExist(String key) throws IOException {
        return storage.ValueExist(key);
    }

}
