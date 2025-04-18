package org.fentanylsolutions.cmotd;

import java.util.ArrayList;
import java.util.List;

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
        if (!FileHandler.readMOTDListFile()
            .isEmpty()) {
            MOTD_LIST_CACHE = FileHandler.readMOTDListFile();
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
