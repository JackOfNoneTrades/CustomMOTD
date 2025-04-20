package org.fentanylsolutions.cmotd;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.fentanylsolutions.cmotd.handler.FileHandler;

public class ModCache {

    private static List<String> MOTD_PLAYERLIST_CACHE = new ArrayList<>();
    private static List<String> MOTD_LIST_CACHE = new ArrayList<>();

    public static void cachePlayerListFile() {
        if (!FileHandler.readPlayerListFile()
            .isEmpty()) {
            MOTD_PLAYERLIST_CACHE = FileHandler.readPlayerListFile();
        } else {
            MOTD_PLAYERLIST_CACHE = new ArrayList<>();
        }
    }

    public static void cacheMOTDListFile() {
        List<String> rawList = FileHandler.readMOTDListFile();
        if (!rawList.isEmpty()) {
            MOTD_LIST_CACHE = rawList.stream()
                .filter(
                    line -> !line.trim()
                        .startsWith("#"))
                .collect(Collectors.toList());
        } else {
            MOTD_LIST_CACHE = new ArrayList<>();
        }
    }

    public static List<String> getPlayerListFileCache() {
        return MOTD_PLAYERLIST_CACHE;
    }

    public static List<String> getMOTDListFileCache() {
        return MOTD_LIST_CACHE;
    }
}
