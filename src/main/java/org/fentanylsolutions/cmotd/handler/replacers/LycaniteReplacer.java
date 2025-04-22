package org.fentanylsolutions.cmotd.handler.replacers;

import net.minecraftforge.common.DimensionManager;

import org.apache.commons.lang3.StringUtils;
import org.fentanylsolutions.cmotd.Config;

import lycanite.lycanitesmobs.ExtendedWorld;

/* Use command `lm mobevent start raptorrampage 0` to test */
public class LycaniteReplacer {

    public static String replaceVars(String text) {
        if (text.contains("{lycanites_event}")) {
            ExtendedWorld worldExt = ExtendedWorld.getForWorld(DimensionManager.getWorld(0));
            if (worldExt == null || worldExt.serverWorldEvent == null || worldExt.serverWorldEvent.mobEvent == null) {
                text = text.replace("{lycanites_event}", "%%REMOVEME%%");
            } else {
                String name = worldExt.serverWorldEvent.mobEvent.name;
                switch (name) {
                    case "raptorrampage":
                        name = "raptor rampage";
                        break;
                    case "satanclaws":
                        name = "satan claws";
                        break;
                    default:
                        break;
                }
                name = StringUtils.capitalize(name);
                text = text.replace("{lycanites_event}", Config.lycanites_event_format.replace("%event%", name));
            }
        }
        return text;
    }
}
