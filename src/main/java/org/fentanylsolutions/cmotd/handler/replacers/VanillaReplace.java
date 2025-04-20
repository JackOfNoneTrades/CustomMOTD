package org.fentanylsolutions.cmotd.handler.replacers;

import net.minecraft.server.MinecraftServer;

import org.apache.commons.lang3.text.WordUtils;
import org.fentanylsolutions.cmotd.util.ProxiedUtils;

public class VanillaReplace {

    public static String replaceVars(String text) {
        MinecraftServer server = ProxiedUtils.getServer();
        text = text.replace(
            "{difficulty}",
            WordUtils.capitalizeFully(
                server.func_147135_j()
                    .name()));
        text = text.replace("{playercount}", Integer.toString(server.getCurrentPlayerCount()));
        text = text.replace("{maxplayers}", Integer.toString(server.getMaxPlayers()));
        text = text.replace("{mcversion}", server.getMinecraftVersion());
        return text;
    }
}
