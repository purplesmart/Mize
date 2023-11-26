package Interfaces;

public interface IStorage<T> {
    T getValue(String key);
    IStorage<T> getNextStorage();
    void setNextStorage(IStorage<T> nextStorage);
    void addValue(String key, T value);
    void addValue(String key, T value, int depth) throws IllegalAccessException;
    boolean IsReadOnly();
    void Init();
    void Propagate(String key, T value);
    boolean ValueExist(String key);
}
