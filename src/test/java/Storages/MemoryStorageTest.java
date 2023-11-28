package Storages;

import Interfaces.IStorage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class MemoryStorageTest {
    int depthCount = 5;
    @Test
    void StorageChainLength() {
        int count = 0;
        IStorage memoryStorage = new MemoryStorage(depthCount, 3);
        while (memoryStorage != null) {
            memoryStorage = memoryStorage.getNextStorage();
            count++;
        }
        assertEquals(depthCount, count);
    }

    @Test
    void StorageInChainNotReadOnly() {

        IStorage memoryStorage = new MemoryStorage(depthCount, 3);
        while (memoryStorage.getNextStorage() != null){
            assertFalse(memoryStorage.IsReadOnly());
            memoryStorage = memoryStorage.getNextStorage();
        }
        assertTrue(memoryStorage.IsReadOnly());
    }

    @Test
    void GetNotValidValue() {
        String notValidKey = "NONVALIDKEY";
        assertThrows(NoSuchElementException.class, () -> {
            IStorage memoryStorage = new MemoryStorage(depthCount, 3);
            memoryStorage.getValue(notValidKey);
        });
    }

    @Test
    void GetValueNoDepth() throws IOException {
        String validKey = "VALIDKEY";
        String validValue = "VALIDVALUE";
        IStorage<String> memoryStorage = new MemoryStorage(depthCount, 3);
        memoryStorage.addValue(validKey,validValue);
        String value = memoryStorage.getValue(validKey);
        assertEquals(validValue, value);
    }

    @Test
    void GetValueWithDepth() throws IllegalAccessException, IOException {
        String validKey = "VALIDKEY";
        String validValue = "VALIDVALUE";
        int validDepth = 5;
        IStorage<String> memoryStorage = new MemoryStorage(depthCount, 3);
        memoryStorage.addValue(validKey,validValue,validDepth);
        String value = memoryStorage.getValue(validKey);
        assertEquals(validValue, value);
    }

    @Test
    void GetValueWithIllegalDepth() throws IllegalAccessException {
        assertThrows(IllegalAccessException.class, () -> {
            String validKey = "VALIDKEY";
            String validValue = "VALIDVALUE";
            int validDepth = 6;
            IStorage<String> memoryStorage = new MemoryStorage(depthCount, 3);
            memoryStorage.addValue(validKey,validValue,validDepth);
        });
    }

    @Test
    void CheckPropagate() throws IllegalAccessException, IOException {
        String validKey = "VALIDKEY";
        String validValue = "VALIDVALUE";
        int validDepth = 3;
        IStorage<String> memoryStorage = new MemoryStorage(depthCount, 3);
        memoryStorage.addValue(validKey,validValue,validDepth);
        memoryStorage.getValue(validKey);
        IStorage<String> currentStorage = memoryStorage;
        for(int i = 1; i < depthCount; i++){
            if(i < validDepth){
                assertFalse(currentStorage.ValueExist(validKey));
            }else{
                assertTrue(currentStorage.ValueExist(validKey));
            }
            currentStorage = currentStorage.getNextStorage();
        }

    }

}