package me.exz.omniocular.util;

import java.util.List;

public class ListHelper {
    public static void AddToList(List<String> tips, String tip) {
        for (String exist : tips) {
            if (exist.equals(tip)) {
                return;
            }
        }
        tips.add(tip);
    }
}
