package com.nekozouneko.nekohub.translator;

import java.util.HashMap;
import java.util.Map;

public final class Localize {

    private final Map<String, Map<String, String>> lang = new HashMap<>();
    private String defaultLanguage = "ja_jp";

    public Localize(String defaultLanguage) {

    }

    public void load(String language, Map<String, String> data) {
        lang.put(language.toLowerCase(), data);
    }

    public void unload(String language) {
        lang.remove(language.toLowerCase());
    }

    public boolean has(String language) {
        return lang.containsKey(language.toLowerCase());
    }

    public String defaultLang(String language) {
        if (has(language)) defaultLanguage = language.toLowerCase();
        return defaultLanguage;
    }

    public String defaultLang() {
        return defaultLanguage;
    }

    public String create(String language, String key, Object... args) {
        return String.format(lang.get(language).get(key), args);
    }


}
