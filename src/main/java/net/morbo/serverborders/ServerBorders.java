package net.morbo.serverborders;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "serverborders",
        name = "ServerBorders",
        version = "1.0-SNAPSHOT",
        description = "Allow travel between server by crossing world border",
        url = "https://github.com/MC-AOF5/server-borders-velocity",
        authors = {"Morb0"}
)
public class ServerBorders {

    @Inject
    public static Logger LOGGER;

    @Inject
    public static ProxyServer SERVER;
}
