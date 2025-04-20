package org.fentanylsolutions.cmotd.handler.replacers;

import org.fentanylsolutions.cmotd.Config;

import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;

public class RandomThingsReplace {

    public static String replaceVars(String text) {
        String inactive = Config.blood_moon_inactive_msg.isEmpty() ? "%%REMOVEME%%" : Config.blood_moon_inactive_msg;
        text = text.replace(
            "{rt_bloodmoon}",
            ServerBloodmoonHandler.INSTANCE.isBloodmoonActive() ? Config.blood_moon_active_msg : inactive);
        return text;
    }
}
