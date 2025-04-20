package org.fentanylsolutions.cmotd;

import org.fentanylsolutions.cmotd.command.CommandReload;
import org.fentanylsolutions.cmotd.handler.EventsHandler;
import org.fentanylsolutions.cmotd.handler.FileHandler;
import org.fentanylsolutions.cmotd.util.ProxiedUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        CustomMOTD.configFile = event.getSuggestedConfigurationFile();
        Config.synchronizeConfiguration(CustomMOTD.configFile);

        CustomMOTD.LOG.info("I am Custom MOTD at version " + Tags.VERSION);

        FileHandler.init();
    }

    public void serverStarting(FMLServerStartingEvent event) {
        if (ProxiedUtils.isSMP() && !ProxiedUtils.isClientSide()) {
            event.registerServerCommand(new CommandReload());
        }

        ModCompat.detectMods();
    }

    public void serverStarted(FMLServerStartedEvent e) {
        if (ProxiedUtils.isSMP() && !ProxiedUtils.isClientSide()) {
            FMLCommonHandler.instance()
                .bus()
                .register(new EventsHandler());
        }
    }
}
