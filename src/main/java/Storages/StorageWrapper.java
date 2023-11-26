package Storages;

import Interfaces.IStorage;

public class StorageWrapper<T> implements IStorage<T> {

    protected IStorage<T> storage;

    public StorageWrapper(IStorage<T> storage){
        this.storage = storage;
    }

    @Override
    public T getValue(String key) {
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
    public void addValue(String key, T value) {
        storage.addValue(key,value);
    }

    @Override
    public void addValue(String key, T value, int depth) throws IllegalAccessException {
        storage.addValue(key,value,depth);
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
    public void Propagate(String key, T value) {
        storage.Propagate(key,value);
    }

    @Override
    public boolean ValueExist(String key) {
        return storage.ValueExist(key);
    }

}
