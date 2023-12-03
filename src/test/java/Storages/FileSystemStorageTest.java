package Storages;

import Entities.JsonDumb;
import Interfaces.IStorage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemStorageTest {

    int depthCount = 5;
    int expirationInterval = 3;

    @Test
    void StorageChainLength() {
        int count = 0;
        IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, expirationInterval);
        while (fileSystemStorage != null) {
            fileSystemStorage = fileSystemStorage.getNextStorage();
            count++;
        }
        assertEquals(depthCount, count);
    }

    @Test
    void StorageInChainNotReadOnly() {

        IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, expirationInterval);
        while (fileSystemStorage.getNextStorage() != null) {
            assertFalse(fileSystemStorage.IsReadOnly());
            fileSystemStorage = fileSystemStorage.getNextStorage();
        }
        assertTrue(fileSystemStorage.IsReadOnly());
    }

    @Test
    void GetNotValidValue() {
        String notValidKey = "NONVALIDKEY";
        assertThrows(NoSuchElementException.class, () -> {
            IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, expirationInterval);
            fileSystemStorage.getValue(notValidKey);
        });
    }

    @Test
    void GetValueNoDepth() throws Exception {
        String validKey = "VALIDKEY";
        JsonDumb jsonDumb= new JsonDumb("DumbName", 16);
        IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, expirationInterval);
        fileSystemStorage.addValue(validKey, jsonDumb);
        JsonDumb returnJsonDumb = fileSystemStorage.getValue(validKey);
        assertEquals(jsonDumb.Name, returnJsonDumb.Name);
        assertEquals(jsonDumb.Age, returnJsonDumb.Age);
    }

    @Test
    void GetValueWithDepth() throws Exception {
        String validKey = "VALIDKEY";
        JsonDumb jsonDumb= new JsonDumb("DumbName", 16);
        int validDepth = 5;
        IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, expirationInterval);
        fileSystemStorage.addValue(validKey, jsonDumb, validDepth);
        JsonDumb returnJsonDumb = fileSystemStorage.getValue(validKey);
        assertEquals(jsonDumb.Name, returnJsonDumb.Name);
        assertEquals(jsonDumb.Age, returnJsonDumb.Age);
    }

    @Test
    void GetValueWithIllegalDepth() throws IllegalAccessException {
        assertThrows(IllegalAccessException.class, () -> {
            String validKey = "VALIDKEY";
            JsonDumb jsonDumb= new JsonDumb("DumbName", 16);
            int validDepth = 6;
            IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, expirationInterval);
            fileSystemStorage.addValue(validKey, jsonDumb, validDepth);
        });
    }

    @Test
    void CheckPropagate() throws Exception {
        String validKey = "VALIDKEY";
        JsonDumb jsonDumb= new JsonDumb("DumbName", 16);
        int validDepth = 3;
        IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, expirationInterval);
        fileSystemStorage.addValue(validKey, jsonDumb, validDepth);
        fileSystemStorage.getValue(validKey);
        IStorage<JsonDumb> currentStorage = fileSystemStorage;
        for (int i = 1; i < depthCount; i++) {
            if (i < validDepth) {
                assertFalse(currentStorage.ValueExist(validKey));
            } else {
                assertTrue(currentStorage.ValueExist(validKey));
            }
            currentStorage = currentStorage.getNextStorage();
        }

    }
}