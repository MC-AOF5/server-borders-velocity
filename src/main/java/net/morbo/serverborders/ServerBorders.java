package net.morbo.serverborders;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "serverborders",
        name = "ServerBorders",
        version = "1.0-SNAPSHOT",
        description = "Allow travel beatween server by crossing world border",
        url = "https://github.com/MC-AOF5/server-borders-velocity",
        authors = {"Morb0"}
)
public class ServerBorders {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
