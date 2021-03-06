package net.elytrapvp.elytrachat.emotes;

import net.elytrapvp.elytrachat.ElytraChat;
import net.elytrapvp.elytrachat.SettingsManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads emotes from the configuration file
 * and stores them for future use.
 */
public class EmoteManager {
    private final ElytraChat plugin;
    private List<Emote> emotes = new ArrayList<>();

    /**
     * Creates the emote manager.
     * @param plugin
     */
    public EmoteManager(ElytraChat plugin) {
        this.plugin = plugin;
        registerEmotes();
    }

    /**
     * Get the list of emotes.
     * @return Emotes.
     */
    public List<Emote> getEmotes() {
        return emotes;
    }

    /**
     * Loops through config to create emote objects.
     */
    public void registerEmotes() {
        SettingsManager settings = plugin.getSettingsManager();
        ConfigurationSection section = settings.getConfig().getConfigurationSection("emotes");

        for(String str : section.getKeys(false)) {
            String identifier = settings.getConfig().getString("emotes." + str + ".identifier");
            String emote = settings.getConfig().getString("emotes." + str + ".emote");
            emotes.add(new Emote(identifier, emote));
        }
    }
}