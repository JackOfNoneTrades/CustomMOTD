package org.fentanylsolutions.cmotd.handler.replacers;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import org.apache.commons.lang3.text.WordUtils;
import org.fentanylsolutions.cmotd.Config;
import org.fentanylsolutions.cmotd.util.ProxiedUtils;

public class VanillaReplace {

    public static String replaceVars(String text) {
        MinecraftServer server = ProxiedUtils.getServer();
        World world = DimensionManager.getWorld(0);
        text = text.replace(
            "{difficulty}",
            WordUtils.capitalizeFully(
                server.func_147135_j()
                    .name()));
        text = text.replace("{playercount}", Integer.toString(server.getCurrentPlayerCount()));
        text = text.replace("{maxplayers}", Integer.toString(server.getMaxPlayers()));
        text = text.replace("{mcversion}", server.getMinecraftVersion());
        if (text.contains("{weather}")) {
            boolean isRaining = world.isRaining();
            boolean isThundering = world.isThundering();
            if (isRaining && isThundering) {
                text = text.replace("{weather}", Config.weather_thunder_msg);
            } else if (isRaining) {
                text = text.replace("{weather}", Config.weather_rain_msg);
            } else {
                text = text.replace("{weather}", Config.weather_clear_msg);
            }
        }
        if (text.contains("{time}") || text.contains("{time_of_the_day}") || text.contains("{lunar_phase}")) {
            long totalTime = world.getWorldTime();
            long dayTicks = totalTime % 24000;

            // -- Time (formatted clock) --
            // In Minecraft, 0 ticks = 6:00 AM, so we shift it by +6000 to align 0 as 0:00
            long adjustedTicks = (dayTicks + 6000) % 24000;

            int hour = (int) (adjustedTicks / 1000);
            int minute = (int) ((adjustedTicks % 1000) * 60 / 1000);

            boolean is24h = Config.use_24h_clock;
            String formattedTime;

            if (is24h) {
                formattedTime = String.format("%02d:%02d", hour, minute);
            } else {
                int displayHour = hour % 12 == 0 ? 12 : hour % 12;
                String amPm = hour < 12 ? "AM" : "PM";
                formattedTime = String.format("%d:%02d %s", displayHour, minute, amPm);
            }

            text = text.replace("{time}", formattedTime);

            // -- Time of the Day --
            String timeOfDay;
            if (dayTicks >= 23000 || dayTicks < 1000) {
                timeOfDay = "Sunrise";
            } else if (dayTicks >= 1000 && dayTicks < 6000) {
                timeOfDay = "Morning";
            } else if (dayTicks >= 6000 && dayTicks < 9000) {
                timeOfDay = "Noon";
            } else if (dayTicks >= 9000 && dayTicks < 12000) {
                timeOfDay = "Afternoon";
            } else if (dayTicks >= 12000 && dayTicks < 13000) {
                timeOfDay = "Sunset";
            } else if (dayTicks >= 13000 && dayTicks < 18000) {
                timeOfDay = "Night";
            } else if (dayTicks >= 18000 && dayTicks < 23000) {
                timeOfDay = "Midnight";
            } else {
                timeOfDay = "Unknown";
            }

            text = text.replace("{time_of_the_day}", timeOfDay);

            // -- Lunar Phase --
            // Moon phase advances every 24000 ticks (1 day), total 8 phases
            int day = (int) (totalTime / 24000) % 8;
            String moonPhase;

            switch (day) {
                case 0:
                    moonPhase = "Full Moon";
                    break;
                case 1:
                    moonPhase = "Waning Gibbous";
                    break;
                case 2:
                    moonPhase = "Third Quarter";
                    break;
                case 3:
                    moonPhase = "Waning Crescent";
                    break;
                case 4:
                    moonPhase = "New Moon";
                    break;
                case 5:
                    moonPhase = "Waxing Crescent";
                    break;
                case 6:
                    moonPhase = "First Quarter";
                    break;
                case 7:
                    moonPhase = "Waxing Gibbous";
                    break;
                default:
                    moonPhase = "Unknown";
                    break;
            }

            text = text.replace("{lunar_phase}", moonPhase);
        }

        return text;
    }
}
