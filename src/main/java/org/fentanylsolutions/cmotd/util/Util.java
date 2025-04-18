package org.fentanylsolutions.cmotd.util;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;

import cpw.mods.fml.common.FMLCommonHandler;

public class Util {

    public static final String fakePlayerUUIDString = "deadbeef-dead-dead-dead-deadbeefdead";
    public static final UUID fakePlayerUUID = UUID.fromString(fakePlayerUUIDString);

    public static boolean isOp(EntityPlayerMP entityPlayerMP) {
        // func_152596_g: canSendCommands
        return FMLCommonHandler.instance()
            .getMinecraftServerInstance()
            .getConfigurationManager()
            .func_152596_g(entityPlayerMP.getGameProfile());
    }
}
