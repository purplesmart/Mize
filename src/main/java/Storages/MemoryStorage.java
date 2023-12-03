package Storages;

import Entities.DataEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class MemoryStorage extends StorageInChainBase<String> {
    private Map<String, DataEntity<String>> dataSource;

    public MemoryStorage(int depth, int expirationInterval) {
        super(depth, expirationInterval);
        Init();
    }

    public void addValue(String key, String value) {
        dataSource.remove(key);
        dataSource.put(key, new DataEntity<>(key, value, getExpirationInterval()));
    }

    @Override
    public void Init() {
        dataSource = new HashMap<>();
    }

    @Override
    public boolean ValueExist(String key) {
        return dataSource.containsKey(key);
    }


    public String getValue(String key) throws Exception {
        DataEntity<String> content = null;
        do {
            if (dataSource.containsKey(key)) {
                content = dataSource.get(key);
            }
            if (content != null && !content.IsExpired()) {
                String value = content.getValue();
                Propagate(key, value);
                return value;
            }
            if (this.getNextStorage() != null) {
                return this.getNextStorage().getValue(key);
            }
        } while (this.getNextStorage() != null);
        throw new NoSuchElementException("No result for key: " + key);
    }
}
