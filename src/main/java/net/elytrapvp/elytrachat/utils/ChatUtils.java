package net.elytrapvp.elytrachat.utils;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    /**
     * A quicker way to send colored messages to a command sender.
     * @param sender Command Sender
     * @param message Message to be sent.
     */
    public static void chat(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    /**
     * Strips color from a Component.
     * @param component Component to strip color from.
     * @return Component with color stripped.
     */
    public static Component stripColor(Component component) {
        String message = Component.text().append(component).content();
        message = ChatColor.stripColor(message);

        return Component.text(message).asComponent();
    }

    public static Component translates(Component component) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        int subVersion = Integer.parseInt(version.replace("1_", "").replaceAll("_R\\d", "").replace("v", ""));

        String message = Component.text().append(component).content();

        if(subVersion >= 16) {
            Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start() + 1, matcher.end());
                message = message.replace("&" + color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }

        return Component.text(ChatColor.translateAlternateColorCodes('&', message)).asComponent();
    }

    /**
     * Translate a String to a colorful String.
     * @param message Message to translate.
     * @return Translated Message.
     */
    public static String translate(String message) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        int subVersion = Integer.parseInt(version.replace("1_", "").replaceAll("_R\\d", "").replace("v", ""));

        if(subVersion >= 16) {
            Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String subMessage = message.substring(matcher.start(), matcher.end());
                String color = message.substring(matcher.start() + 1, matcher.end());

                String[] parts = message.split(subMessage);
                message = parts[0] + "<color:" + color + ">";

                for(int i = 1; i < parts.length; i++) {
                    message += parts[i];
                }

                //message = message.replace("&" + color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}