package me.ryzeon.redis.redis.listener;

import com.google.gson.Gson;
import me.ryzeon.redis.Example;
import me.ryzeon.redis.utils.CC;
import me.ryzeon.redis.utils.RedisMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by Ryzeon
 * Project: RedisExample
 * Date: 13/09/2020 @ 19:59
 * Template by Elp1to
 */

public class RedisListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                RedisMessage redisMessage = new Gson().fromJson(message,RedisMessage.class);
                /*
                A switch is made to assign the action for each type of Payload, it can also be done by "if"
                 */
                switch (redisMessage.getPayload()){
                    case SERVER_MANAGER:{
                        String SERVER = redisMessage.getParam("SERVER");
                        String STATUS = redisMessage.getParam("STATUS");
                        Bukkit.broadcastMessage(CC.translate("&d" + SERVER + " &fwill be " + STATUS + " &fin 5 seconds."));
                        break;
                    }
                    case PLAYER_JOIN:{
                        String PLAYER = redisMessage.getParam("PLAYER");
                        String SERVER = redisMessage.getParam("SERVER");
                        Bukkit.broadcastMessage(CC.translate("&d" + PLAYER + " &fjoin to &d" + SERVER + "!"));
                        break;
                    }
                    default:
                        Example.getInstance().getLogger().info("[Redis] The message was received, but there was no response");
                        break;
                }
            }
        }.runTask(Example.getInstance());
    }
}
