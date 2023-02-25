package com.nekozouneko.nekohub;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class Util {

    private Util() {}

    public static byte[] toByteArr(Object obj) throws IOException {
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(arr);

        out.writeObject(obj);

        return arr.toByteArray();
    }

    public static List<String> ignoreCaseTabComp(List<String> suggest, String arg) {
        List<String> res = new ArrayList<>();
        for (String s : suggest) {
            if (s.toLowerCase().startsWith(arg.toLowerCase())) {
                res.add(s);
            }
        }
        return res;
    }

    public static String replaceAltCodes(String s) {
        return s.replaceAll("#([0-9A-F])([0-9A-F])([0-9A-F])([0-9A-F])([0-9A-F])([0-9A-F])", "§x§$1§$2§$3§$4§$5§$6").replaceAll("#([0-9A-F])([0-9A-F])([0-9A-F])", "§x§$1§$2§$3").replaceAll("&([0-9A-FKLMNORXa-fklmnorx])", "§$1");
    }

    public static <T> T orElse(T obj, T def) {
        return obj != null ? obj : def;
    }

    public static boolean isUUID(String s) {
        return s.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}") || s.matches("[0-9a-fA-F]{32}");
    }

    @SafeVarargs
    public static <K, V> boolean containsKeys(Map<K, V> map, K... keys) {
        if (keys.length == 0) return false;

        boolean b = true;

        for (K key : keys) {
            b = b && map.containsKey(key);
        }

        return b;
    }

}
