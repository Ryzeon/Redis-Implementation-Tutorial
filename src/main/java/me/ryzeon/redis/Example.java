package me.ryzeon.redis;

import lombok.Getter;
import me.ryzeon.redis.listener.PlayerListener;
import me.ryzeon.redis.redis.Redis;
import me.ryzeon.redis.redis.payload.Payload;
import me.ryzeon.redis.utils.CC;
import me.ryzeon.redis.utils.RedisMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Ryzeon
 * Project: RedisExample
 * Date: 13/09/2020 @ 19:46
 * Template by Elp1to
 */

@Getter
public class Example extends JavaPlugin {
    /*
    Any Question send me private message on twitter -> @Ryzeon_
    or discord -> Ryzeon#1111

    I do this tutorial on how to use redis using methods found in bukkit forums and others made by me

    The objective is that others manage to implement redis to their plugins easier: D
     */

    @Getter private static Example instance;

    private Redis redisManager;

    private String json;

    @Override
    public void onEnable() {
        instance = this;
        this.getConfig().options().copyDefaults(false);
        this.reloadConfig();

        redisManager = new Redis();

        redisManager.connect();

        Bukkit.broadcastMessage(CC.translate("&aEnabled!"));
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);

        json = new RedisMessage(Payload.SERVER_MANAGER)
                .setParam("SERVER",getServer().getName())
                .setParam("STATUS","§aonline").toJSON();

        redisManager.write(json);
    }

    @Override
    public void onDisable() {
        instance = null;

        if (redisManager.isActive()){
            json = new RedisMessage(Payload.SERVER_MANAGER).setParam("SERVER",getServer().getName()).setParam("STATUS","§coffline").toJSON();

            redisManager.write(json);
        }

        redisManager.disconnect();
    }
}
