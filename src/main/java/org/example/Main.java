package org.example;

import Entities.JsonDumb;
import Interfaces.IStorage;
import Storages.FileSystemStorage;
import Storages.MemoryStorage;
import Storages.WebServiceStorage;

public class Main {
    public static void main(String[] args) {
        int depthCount = 5;
        int memoryExpirationInterval  =1;
        int fileSystemExpirationInterval = 4;
        int webServiceExpirationInterval = 0;
        IStorage<String> memoryStorage = new MemoryStorage(depthCount, memoryExpirationInterval);
        IStorage<JsonDumb> fileSystemStorage = new FileSystemStorage(depthCount, fileSystemExpirationInterval);
        IStorage<Double> webServiceStorage= new WebServiceStorage(depthCount, webServiceExpirationInterval);

    }
}