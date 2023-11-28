package Storages;

import Entities.DataEntity;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileSystemStorage extends StorageInChainBase<JsonObject> {
    String folderPath = "c:/somefolder";

    private Map<String, DataEntity<String>> dataSource;

    @Override
    public boolean ValueExist(String key) {
        return dataSource.containsKey(key);
    }

    private Gson gson;

    public FileSystemStorage(int depth, int expirationInterval) {
        super(depth, expirationInterval);
        Init();
    }

    @Override
    public void Init() {
        dataSource= new HashMap<>();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }


    public JsonObject getValue(String key) {
        String path = folderPath + "/" + key + ".json";
        if (dataSource.containsKey(key)) {
            DataEntity<String> item = dataSource.get(key);
            if (!item.IsExpired()) {
                return loadJSONFile(item.getValue());
            }
        }
        return null;
    }

    private JsonObject loadJSONFile(String filePath) {
        return gson.fromJson(filePath, JsonObject.class);
    }


    public void addValue(String key, JsonObject value) throws IOException {
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

    private String saveFileReturnFullPath(String key, JsonObject object) throws IOException {
        //Save
        String path = folderPath + "/" + key + ".json";
        String jsonObject = gson.toJson(object);
        try(FileWriter fileWriter = new FileWriter(path)){
            // Write JSON data to the file
            gson.toJson(jsonObject, fileWriter);
        }
        return path;
    }

}
