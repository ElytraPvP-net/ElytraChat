package net.elytrapvp.elytrachat.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.elytrapvp.elytrachat.ElytraChat;
import net.elytrapvp.elytrachat.SettingsManager;
import net.elytrapvp.elytrachat.emotes.Emote;
import net.elytrapvp.elytrachat.utils.ChatUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {
    private final ElytraChat plugin;

    public AsyncChatListener(ElytraChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        // Exit if the event was cancelled by another plugin.
        if(event.isCancelled()) {
            return;
        }

        SettingsManager settings = plugin.getSettingsManager();
        Player player = event.getPlayer();

        // Sets the default chat format to "default" incase no permissions found.
        String format = "default";

        // Loops through all the chat formats to find the highest format allowed.
        for(String str : settings.getFormats().getConfigurationSection("formats").getKeys(false)) {
            if(player.hasPermission("format." + str)) {
                format = str;
                break;
            }
        }

        String chatFormat = "";
        ConfigurationSection section = settings.getFormats().getConfigurationSection("formats." + format);
        for(String str : section.getKeys(true)) {
            chatFormat += settings.getFormats().getString("formats." + format + "." + str);
        }

        String chatMessage = PlainTextComponentSerializer.plainText().serialize(event.message());

        chatMessage = ChatUtils.translate(chatMessage);
        if(!player.hasPermission("elytrachat.color")) {
            chatMessage = ChatColor.stripColor(chatMessage);
            chatMessage = MiniMessage.get().stripTokens(chatMessage);
        }

        if(player.hasPermission("elytrachat.emotes")) {
            chatMessage = translateText(chatMessage);
        }

        for(Audience viewer : event.viewers()) {
            viewer.sendMessage(MiniMessage.get().parse(ChatUtils.translate(PlaceholderAPI.setPlaceholders(player, chatFormat + chatMessage))));
        }
        event.viewers().clear();
    }

    /**
     * Translate text into it's emote form.
     * @param str String to translate
     * @return Translated string
     */
    public String translateText(String str) {
        for(Emote emote : ElytraChat.getEmoteManager().getEmotes()) {
            str = str.replace(emote.getIdentifier(), emote.getEmote());
        }

        return str;
    }
}