package org.fentanylsolutions.cmotd.handler.replacers;

public class SpecialReplace {

    public static String replaceVars(String text) {
        if (text.startsWith("\\#")) {
            text = text.substring(1);
        }
        text = text.replace("\\|", "%%PIPE%%");
        text = text.replace("|", "\n");
        text = text.replace("%%PIPE%%", "|");
        text = text.replace("\\&", "%%AND%%");
        text = text.replace("&", "§");
        text = text.replace("%%AND%%", "&");
        return text;
    }
}
