package org.fentanylsolutions.cmotd;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(
    modid = CustomMOTD.MODID,
    version = Tags.VERSION,
    name = "Custom MOTD",
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*")
public class CustomMOTD {

    public static final String MODID = "cmotd";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static File configFile = null;

    @Mod.Instance("cmotd")
    public static CustomMOTD INSTANCE;

    @SidedProxy(
        clientSide = "org.fentanylsolutions.cmotd.ClientProxy",
        serverSide = "org.fentanylsolutions.cmotd.CommonProxy")
    public static CommonProxy proxy;

    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        INSTANCE = this;
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent e) {
        proxy.serverStarted(e);
    }
}
