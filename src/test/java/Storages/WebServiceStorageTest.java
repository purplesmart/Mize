package Storages;

import Interfaces.IStorage;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class WebServiceStorageTest {


    static int depthCount = 5;
    static int expirationInterval = 3;

    static IStorage<Double> webServiceStorage= new WebServiceStorage(depthCount, expirationInterval);

    @Test
    void OnlyOneStorage() {
        assertNull(webServiceStorage.getNextStorage());
    }

    @Test
    void StorageIsReadOnly() {
        assertTrue(webServiceStorage.IsReadOnly());
    }

    @Test
    void addValue() {
        assertThrows(IllegalAccessException.class, () -> {
            webServiceStorage.addValue("ZZZ",0.4);
        });
    }

    @Test
    void valueExistTrue() throws IOException {
        boolean result = webServiceStorage.ValueExist("EUR");
        assertTrue(result);
    }

    @Test
    void valueExistFalse() throws IOException {
        boolean result = webServiceStorage.ValueExist("ZZZ");
        assertFalse(result);
    }

    @Test
    void getValueWithValidKey() throws Exception {
        Double result = webServiceStorage.getValue("EUR");
        assertTrue(result != null && Double.isFinite(result));
    }

    @Test
    void getValueWithoutValidKey() throws Exception {
        assertThrows(NoSuchElementException.class, () -> {
            webServiceStorage.getValue("ZZZ");
        });
    }
}