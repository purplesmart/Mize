package Interfaces;

import java.io.IOException;

public interface IStorage<T> {
    T getValue(String key) throws Exception;
    IStorage<T> getNextStorage();
    void setNextStorage(IStorage<T> nextStorage);
    void addValue(String key, T value) throws IOException, IOException, IllegalAccessException;
    void addValue(String key, T value, int depth) throws IllegalAccessException, IOException;
    boolean IsReadOnly();
    void Init();
    void Propagate(String key, T value) throws Exception;
    boolean ValueExist(String key) throws IOException;
}
