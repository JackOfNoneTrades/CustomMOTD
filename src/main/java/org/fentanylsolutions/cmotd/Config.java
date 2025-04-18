package org.fentanylsolutions.cmotd;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String no_players_msg = "So empty :(";
    public static int reload_interval = 60;
    public static int server_stat_reload_interval = 60;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        no_players_msg = configuration.getString(
            "no_players_msg",
            Configuration.CATEGORY_GENERAL,
            no_players_msg,
            "Empty player list entry. Leave blank to skip displaying an empty list altogether.");
        reload_interval = configuration.getInt(
            "reload_interval",
            Configuration.CATEGORY_GENERAL,
            reload_interval,
            -1,
            Integer.MAX_VALUE,
            "Seconds after which custommotdlist.txt and customplayerlist.txt are reloaded from disk.");
        server_stat_reload_interval = configuration.getInt(
            "server_stat_reload_interval",
            Configuration.CATEGORY_GENERAL,
            server_stat_reload_interval,
            -1,
            Integer.MAX_VALUE,
            "Seconds after which server stats (such as amount of players or difficulty) are refreshed.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
