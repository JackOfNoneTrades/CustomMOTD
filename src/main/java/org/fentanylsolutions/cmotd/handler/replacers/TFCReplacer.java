package org.fentanylsolutions.cmotd.handler.replacers;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;

public class TFCReplacer {

    public static String replaceVars(String text) {
        text = text.replace("{tfc_season}", TFC_Time.getSeason());
        text = text.replace("{tfc_day_of_month}", String.valueOf(TFC_Time.getDayOfMonth()));
        text = text.replace("{tfc_day_of_week}", String.valueOf(TFC_Time.getDayOfWeek()));
        text = text.replace("{tfc_day_of_year}", String.valueOf(TFC_Time.getDayOfYear()));
        text = text.replace("{tfc_month}", String.valueOf(TFC_Time.getMonth()));
        text = text.replace("{tfc_month_name}", getMonthName(TFC_Time.getMonth()));
        text = text.replace("{tfc_year}", String.valueOf(TFC_Time.getYear()));
        text = text.replace("{tfc_hour}", String.valueOf(TFC_Time.getHour()));
        if (text.contains("{tfc_temperature}")) {
            World world = DimensionManager.getWorld(0);
            text = text.replace("{tfc_temperature}", String.valueOf(TFC_Climate.getBioTemperature(world, 0, 0)));
        }

        return text;
    }

    public static String getMonthName(int month) {
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
        return months[month];
    }
}
