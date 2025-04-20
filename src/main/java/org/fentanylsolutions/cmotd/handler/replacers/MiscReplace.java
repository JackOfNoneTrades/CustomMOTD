package org.fentanylsolutions.cmotd.handler.replacers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fentanylsolutions.cmotd.util.TextUtil;

public class MiscReplace {

    public static String replaceVars(String text) {
        text = text.replace("{radio}", TextUtil.RADIO_BARS);
        text = randomFromString(text);
        return text;
    }

    private static String randomFromString(String list) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.length(); i++) {
            if (i < list.length() - 7 && list.startsWith("{random", i)) {
                List<String> randomWords = new ArrayList<>();
                String temp = "";
                int counter = i + 8;
                int openBrackets = 1;
                while (!(String.valueOf(list.charAt(counter))
                    .equals("}") && openBrackets == 1)) {
                    if (list.charAt(counter) == ',') {
                        randomWords.add(temp);
                        temp = "";
                    } else if (list.charAt(counter + 1) == '}' && openBrackets == 1) {
                        temp += list.charAt(counter);
                        randomWords.add(temp);
                    } else {
                        temp += list.charAt(counter);
                        if (list.charAt(counter) == '{') {
                            openBrackets++;
                        } else if (list.charAt(counter) == '}') {
                            openBrackets--;
                        }
                    }
                    counter++;
                }
                for (int j = 0; j < randomWords.size(); j++) {
                    if (Character.isSpaceChar(
                        randomWords.get(j)
                            .charAt(0))) {
                        randomWords.set(
                            j,
                            randomWords.get(j)
                                .substring(1));
                    }
                    if (!randomWords.get(j)
                        .isEmpty() && Character.isSpaceChar(
                            randomWords.get(j)
                                .charAt(
                                    randomWords.get(j)
                                        .length() - 1))) {
                        randomWords.set(
                            j,
                            randomWords.get(j)
                                .substring(
                                    0,
                                    randomWords.get(j)
                                        .length() - 1));
                    }
                }
                Random rand = new Random();
                res.append(randomWords.get(rand.nextInt(randomWords.size())));
                // i += counter - 8;
                i = counter;
            } else {
                res.append(list.charAt(i));
            }
        }
        return res.toString();
    }
}
