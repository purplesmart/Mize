package Storages;

import Entities.DataEntity;
import java.util.HashMap;
import java.util.Map;

public class FileSystemStorage extends StorageInChainBase<Object> {
    String folderPath = "c:/somefolder";

    private Map<String, DataEntity<String>> dataSource;

    @Override
    public boolean ValueExist(String key) {
        return dataSource.containsKey(key);
    }

    public FileSystemStorage(int depth, int expirationInterval) {
        super(depth, expirationInterval);
        Init();
    }

    @Override
    public void Init() {
        dataSource= new HashMap<>();
    }


    public Object getValue(String key) {
        String path = folderPath + "/" + key + ".json";
        if (dataSource.containsKey(key)) {
            DataEntity<String> item = dataSource.get(key);
            if (!item.IsExpired()) {
                return loadJSONFile(item.getValue());
            }
        }
        return null;
    }

    private Object loadJSONFile(String filePath) {
        // load JSON from file system
        return null;
    }


    public void addValue(String key, Object value) {
        if (dataSource.containsKey(key)) {
            String filePath = dataSource.get(key).getValue();
            deleteFile(filePath);
            dataSource.remove(key);
        }
        dataSource.put(key, new DataEntity<>(key, saveFileReturnFullPath(key, value)));
    }

    private void deleteFile(String path){
        // Deleting file
    }

    private String saveFileReturnFullPath(String key, Object value) {
        //Save
        String path = folderPath + "/" + key + ".json";
        // file save ( path )
        return path;
    }

}
