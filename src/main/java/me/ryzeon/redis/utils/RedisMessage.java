package me.ryzeon.redis.utils;

import com.google.gson.Gson;
import lombok.Getter;
import me.ryzeon.redis.redis.payload.Payload;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryzeon
 * Project: RedisExample
 * Date: 13/09/2020 @ 19:47
 * Template by Elp1to
 */

public class RedisMessage {

    /*
    Credits to Brulin to for providing a RedisMessage method -> https://pastebin.com/kg614Sy3
     */

    @Getter
    private Payload payload;
    private Map<String, String> params;

    public RedisMessage(Payload payload) {
        this.payload = payload;
        params = new HashMap<>();
    }

    public RedisMessage setParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public String getParam(String key) {
        if (containsParam(key)) {
            return params.get(key);
        }
        return null;
    }

    public boolean containsParam(String key) {
        return params.containsKey(key);
    }

    public void removeParam(String key) {
        if (containsParam(key)) {
            params.remove(key);
        }
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}
