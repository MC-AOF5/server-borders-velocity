package net.morbo.serverborders.redis;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.morbo.serverborders.ServerBorders;
import net.morbo.serverborders.config.Configs;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Optional;
import java.util.UUID;

public class RedisListener {
    public boolean isActiveAndEnabled;

    public final void listen() {
        new Thread(() -> {
            isActiveAndEnabled = true;
            while (isActiveAndEnabled) {

                Jedis subscriber;
                if (Configs.redisPassword.isEmpty()) {
                    subscriber = new Jedis(Configs.redisHost,
                            Configs.redisPort,
                            0,
                            Configs.redisSSL);
                } else {
                    final JedisClientConfig config = DefaultJedisClientConfig.builder()
                            .password(Configs.redisPassword)
                            .ssl(Configs.redisSSL)
                            .timeoutMillis(0)
                            .build();

                    subscriber = new Jedis(Configs.redisHost,
                            Configs.redisPort,
                            config);
                }
                subscriber.connect();

                ServerBorders.LOGGER.info("Enabled Redis listener successfully!");
                try {
                    subscriber.subscribe(new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            if (!channel.equals(RedisMessage.REDIS_CHANNEL)) {
                                return;
                            }

                            handleMessage(new RedisMessage(message));
                        }
                    }, RedisMessage.REDIS_CHANNEL);
                } catch (JedisConnectionException connectionException) {
                    ServerBorders.LOGGER.error("A connection exception occurred with the Jedis listener");
                    connectionException.printStackTrace();
                } catch (JedisException jedisException) {
                    isActiveAndEnabled = false;
                    ServerBorders.LOGGER.error("An exception occurred with the Jedis listener");
                    jedisException.printStackTrace();
                } finally {
                    subscriber.close();
                }
            }
        }, "Redis Subscriber").start();
    }

    public void handleMessage(RedisMessage message) {
        Optional<RegisteredServer> server = ServerBorders.SERVER.getServer("aof1");
        if (server.isEmpty()) {
            ServerBorders.LOGGER.error("Server " + "aof1" + " not found");
        }

        Optional<Player> player = ServerBorders.SERVER.getPlayer(message.getPlayerUUID());
        if (player.isEmpty()) {
            ServerBorders.LOGGER.error("Player " + message.getPlayerUUID() + " not found");
        }

        player.get().createConnectionRequest(server.get()).fireAndForget();
        ServerBorders.LOGGER.info("Player " + message.getPlayerUUID() + " has been moved to " + "aof1" + " server");
    }
}
