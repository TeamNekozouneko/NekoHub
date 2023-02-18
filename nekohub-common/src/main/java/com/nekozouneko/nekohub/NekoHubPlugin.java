package com.nekozouneko.nekohub;

import java.io.File;

public interface NekoHubPlugin {

    public static enum Depends {
        LUCKPERMS,
        VAULT_CHAT,
        VAULT_ECONOMY
    }

    public File getPluginDataFolder();

    public File getLanguageFolder();

    public boolean isDependEnabled(Depends depend);

}
