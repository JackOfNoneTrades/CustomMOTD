package org.fentanylsolutions.cmotd;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String no_players_msg = "So empty :(";
    public static int reload_interval = 60;
    public static int server_stat_reload_interval = 60;

    public static String weather_thunder_msg = "Thunderstorm";
    public static String weather_rain_msg = "Rain";
    public static String weather_clear_msg = "Clear";

    public static boolean use_24h_clock = true;

    public static String blood_moon_active_msg = "Blood moon has risen!";
    public static String blood_moon_inactive_msg = "";

    public static String lycanites_event_format = "Current Lycanite event: %event%";

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

        weather_clear_msg = configuration.getString(
            "weather_clear_msg",
            Configuration.CATEGORY_GENERAL,
            weather_clear_msg,
            "How to format {weather} when the weather is clear.");

        weather_rain_msg = configuration.getString(
            "weather_rain_msg",
            Configuration.CATEGORY_GENERAL,
            weather_rain_msg,
            "How to format {weather} when it is rainig.");

        use_24h_clock = configuration.getBoolean(
            "use_24h_clock",
            Configuration.CATEGORY_GENERAL,
            use_24h_clock,
            "Whether to use the 24 hour format for {time}.");

        weather_thunder_msg = configuration.getString(
            "weather_thunder_msg",
            Configuration.CATEGORY_GENERAL,
            weather_thunder_msg,
            "How to format {weather} when there is a thunderstorm.");

        blood_moon_active_msg = configuration.getString(
            "blood_moon_active_msg",
            Configuration.CATEGORY_GENERAL,
            blood_moon_active_msg,
            "How to format {rt_bloodmoon} (when the Random Things blood moon is active).");

        blood_moon_inactive_msg = configuration.getString(
            "blood_moon_inactive_msg",
            Configuration.CATEGORY_GENERAL,
            blood_moon_inactive_msg,
            "How to format {rt_bloodmoon} (when the Random Things blood moon is inactive).");

        lycanites_event_format = configuration.getString(
            "lycanites_event_format",
            Configuration.CATEGORY_GENERAL,
            lycanites_event_format,
            "How to format {lycanites_event}. %event% is replaced by the event name.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
