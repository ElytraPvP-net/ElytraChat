package net.elytrapvp.elytrachat;

import net.elytrapvp.elytrachat.chat.AsyncChatListener;
import net.elytrapvp.elytrachat.emotes.EmoteManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraChat extends JavaPlugin {
    private static EmoteManager emoteManager;
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        settingsManager = new SettingsManager(this);
        emoteManager = new EmoteManager(this);

        Bukkit.getServer().getPluginManager().registerEvents(new AsyncChatListener(this), this);
    }

    public static EmoteManager getEmoteManager() {
        return emoteManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}