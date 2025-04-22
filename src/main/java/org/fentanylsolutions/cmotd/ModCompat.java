package org.fentanylsolutions.cmotd;

import cpw.mods.fml.common.Loader;

public class ModCompat {

    public static boolean RTLoaded;
    public static boolean SereneSeasonsLoaded;
    public static boolean LycanitesLoaded;
    public static boolean TFCLoaded;

    public static void detectMods() {
        RTLoaded = Loader.isModLoaded("RandomThings");
        SereneSeasonsLoaded = Loader.isModLoaded("sereneseasons");
        LycanitesLoaded = Loader.isModLoaded("lycanitesmobs");
        TFCLoaded = Loader.isModLoaded("terrafirmacraft");
    }
}
