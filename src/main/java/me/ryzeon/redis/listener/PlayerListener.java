package me.ryzeon.redis.listener;

import me.ryzeon.redis.Example;
import me.ryzeon.redis.redis.payload.Payload;
import me.ryzeon.redis.utils.RedisMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Ryzeon
 * Project: RedisExample
 * Date: 13/09/2020 @ 20:19
 * Template by Elp1to
 */

public class PlayerListener implements Listener {

    /*
    Example to how to send redis message
     */

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        String json = new RedisMessage(Payload.PLAYER_JOIN)
                .setParam("PLAYER",event.getPlayer().getName())
                .setParam("SERVER", Example.getInstance().getServer().getName()).toJSON();

        Example.getInstance().getRedisManager().write(json);
    }
}
