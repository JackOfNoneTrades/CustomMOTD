package org.fentanylsolutions.cmotd;

import cpw.mods.fml.common.Loader;

public class ModCompat {

    public static boolean RTLoaded;

    public static void detectMods() {
        RTLoaded = Loader.isModLoaded("RandomThings");
    }
}
