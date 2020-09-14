package me.ryzeon.redis.redis;

import lombok.Getter;
import me.ryzeon.redis.Example;
import me.ryzeon.redis.redis.listener.RedisListener;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Ryzeon
 * Project: RedisExample
 * Date: 13/09/2020 @ 19:46
 * Template by Elp1to
 */

@Getter
public class Redis {

    JedisPool jedisPool;

    RedisListener redisListener;

    private final String ip = Example.getInstance().getConfig().getString("REDIS.HOST");

    private final int port = Example.getInstance().getConfig().getInt("REDIS.PORT");

    private final String password = Example.getInstance().getConfig().getString("REDIS.AUTHENTICATION.PASSWORD");

    private final boolean auth = Example.getInstance().getConfig().getBoolean("REDIS.AUTHENTICATION.ENABLED");

    @Getter
    private boolean active = false;

    public void connect() {
        try {
            Example.getInstance().getLogger().info("Connecting to redis");
            this.jedisPool = new JedisPool("localhost", 6379);
            Jedis jedis = this.jedisPool.getResource();
            if (auth){
                if (password != null || !password.equals(""))
                    jedis.auth(this.password);
            }
            this.redisListener = new RedisListener();
            (new Thread(() -> jedis.subscribe(this.redisListener, new String[] { "Example" }))).start();
            jedis.connect();
            active = true;
            Example.getInstance().getLogger().info("Successfully redis connection.");
        } catch (Exception e) {
            Example.getInstance().getLogger().info("Error in redis connection.");
            active = false;
        }
    }

    public void disconnect() {
        jedisPool.destroy();
        this.redisListener.unsubscribe();
    }

    public void write(String json){
        Jedis jedis = this.jedisPool.getResource();
        try {
            if (auth){
                if (password != null || !password.equals(""))
                    jedis.auth(this.password);
            }
            jedis.publish("Example",json);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }
}
