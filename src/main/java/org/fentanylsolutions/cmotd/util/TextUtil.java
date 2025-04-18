package org.fentanylsolutions.cmotd.util;

import net.minecraft.util.EnumChatFormatting;

public class TextUtil {

    public static final String RADIO_BARS = EnumChatFormatting.OBFUSCATED + "|||||||" + EnumChatFormatting.RESET;
    public static final String LINE_SEP = "==========================";
    public static final String BLACK = EnumChatFormatting.BLACK.toString();
    public static final String D_BLUE = EnumChatFormatting.DARK_BLUE.toString();
    public static final String D_GREEN = EnumChatFormatting.DARK_GREEN.toString();
    public static final String D_AQUA = EnumChatFormatting.DARK_AQUA.toString();
    public static final String D_RED = EnumChatFormatting.DARK_RED.toString();
    public static final String D_PURPLE = EnumChatFormatting.DARK_PURPLE.toString();
    public static final String GOLD = EnumChatFormatting.GOLD.toString();
    public static final String GRAY = EnumChatFormatting.GRAY.toString();
    public static final String D_GRAY = EnumChatFormatting.DARK_GRAY.toString();
    public static final String BLUE = EnumChatFormatting.BLUE.toString();
    public static final String GREEN = EnumChatFormatting.GREEN.toString();
    public static final String AQUA = EnumChatFormatting.AQUA.toString();
    public static final String RED = EnumChatFormatting.RED.toString();
    public static final String L_PURPLE = EnumChatFormatting.LIGHT_PURPLE.toString();
    public static final String YELLOW = EnumChatFormatting.YELLOW.toString();
    public static final String WHITE = EnumChatFormatting.WHITE.toString();
    public static final String RESET = EnumChatFormatting.RESET.toString();
    public static final String OBF = EnumChatFormatting.OBFUSCATED.toString();
    public static final String BOLD = EnumChatFormatting.BOLD.toString();
    public static final String UNDER = EnumChatFormatting.UNDERLINE.toString();
    public static final String STRIKE = EnumChatFormatting.STRIKETHROUGH.toString();
    public static final String ITALIC = EnumChatFormatting.ITALIC.toString();

    public static String wrapRadioBars(String text) {
        return GOLD + RADIO_BARS + WHITE + BOLD + text + RESET + GOLD + RADIO_BARS;
    }

    public static String lineSep(String color) {
        return color + "==========================" + RESET;
    }
}
