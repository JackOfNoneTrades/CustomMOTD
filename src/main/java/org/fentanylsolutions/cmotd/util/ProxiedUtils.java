package org.fentanylsolutions.cmotd.util;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class ProxiedUtils {

    public static boolean isSMP() {
        return (FMLCommonHandler.instance()
            .getMinecraftServerInstance() != null && FMLCommonHandler.instance()
                .getMinecraftServerInstance()
                .isDedicatedServer());
    }

    public static boolean isClientSide() {
        return (FMLCommonHandler.instance()
            .getSide() == Side.CLIENT);
    }

    public static MinecraftServer getServer() {
        return MinecraftServer.getServer();
    }
}
