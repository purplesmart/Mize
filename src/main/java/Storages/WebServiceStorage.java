package Storages;

import com.google.gson.*;
import org.asynchttpclient.*;
import org.asynchttpclient.netty.NettyResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class WebServiceStorage extends StorageInChainBase<Double>{

    final String RATES_PROPERTY_NAME = "rates";
    String AppId;

    private Gson gson;

    public WebServiceStorage(int depth, int expirationInterval) {
        super(0,-1);
        Init();
    }

    @Override
    public void Init() {
        AppId = "0cf998325ef741da8f428054d6c846b6";
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void addValue(String key, Double value) throws IllegalAccessException {
       throw new IllegalAccessException("Thee storage doesn't contain AddValue");
    }

    @Override
    public boolean ValueExist(String key){
        try {
            getValue(key);
            return true;
        }catch (Exception ex){
            return false;
        }
    }


    public Double getValue(String key) throws IOException {
        try (AsyncHttpClient client = new DefaultAsyncHttpClient()) {
            NettyResponse result = (NettyResponse) client.prepareGet("https://openexchangerates.org/api/latest.json?app_id=" + AppId + "&symbols=" + key)
                    .setHeader("accept", "application/json")
                    .execute()
                    .toCompletableFuture()
                    .get();

            JsonObject jsonObject = gson.fromJson(result.getResponseBody(), JsonObject.class);

            JsonObject ratesObject = jsonObject.getAsJsonObject(RATES_PROPERTY_NAME);
            if (ratesObject.has(key)) {
                return ratesObject.get(key).getAsDouble();
            } else {
                throw new NoSuchElementException("The Coin sign does not exist");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException("Error occurred while fetching data: " + e.getMessage(), e);
        }
    }
}
