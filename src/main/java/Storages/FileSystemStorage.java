package Storages;

import Entities.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;
import java.util.UUID;

public class FileSystemStorage extends StorageInChainBase<JsonDumb> {
    private String folderPath;
    private UUID uniqueID;
    String filePathMize;

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
        dataSource = new HashMap<>();
        gson = new GsonBuilder().setPrettyPrinting().create();
        folderPath = System.getProperty("user.home");
        uniqueID = UUID.randomUUID();
        filePathMize = "MizeFiles";
    }


    public JsonDumb getValue(String key) throws Exception {
        DataEntity<String> dataEntity = null;
        do {
            if (dataSource.containsKey(key)) {
                dataEntity = dataSource.get(key);
            }
            if (dataEntity != null && !dataEntity.IsExpired()) {
                JsonDumb jsonDumb = loadJSONFile(dataEntity.getValue());
                Propagate(key, jsonDumb);
                return jsonDumb;
            }
            if (this.getNextStorage() != null) {
                return this.getNextStorage().getValue(key);
            }
        } while (this.getNextStorage() != null);
        throw new NoSuchElementException("No result for key: " + key);
    }

    private JsonDumb loadJSONFile(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            JsonDumb jsonDumb = gson.fromJson(reader, JsonDumb.class);
            return jsonDumb;
        }
    }

    public void addValue(String key, JsonDumb value) throws IOException {
        String filePath;
        if (dataSource.containsKey(key)) {
            DataEntity<String> dataEntity = dataSource.get(key);
            filePath = dataEntity.getValue();
            dataSource.remove(key);
        } else {
            filePath = saveFileReturnFullPath(key, value);
        }
        dataSource.put(key, new DataEntity<>(key, filePath, getExpirationInterval()));
    }

    private String saveFileReturnFullPath(String key, JsonDumb jsonDumb) throws IOException {
        String path = folderPath + "/" + filePathMize + "/" + uniqueID.toString() + key + ".json";
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(jsonDumb, fileWriter);
        }
        return path;
    }
}
