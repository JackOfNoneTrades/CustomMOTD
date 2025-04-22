package org.fentanylsolutions.cmotd.handler.replacers;

import net.minecraft.server.MinecraftServer;

import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

public class SereneSeasonsReplace {

    public static String replaceVars(String text) {
        Season.SubSeason subSeason = SeasonHelper.getSeasonState(
            MinecraftServer.getServer()
                .worldServerForDimension(0))
            .getSubSeason();
        text = text.replace("{serene_season}", formatEnumName(subSeason.name()));
        return text;
    }

    private static String formatEnumName(String enumName) {
        String[] parts = enumName.toLowerCase()
            .split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(Character.toUpperCase(part.charAt(0)))
                .append(part.substring(1))
                .append(" ");
        }
        return builder.toString()
            .trim();
    }
}
