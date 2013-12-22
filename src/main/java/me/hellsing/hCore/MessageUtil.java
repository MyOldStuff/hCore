package me.hellsing.hCore;

import org.bukkit.entity.Player;

import java.util.regex.Pattern;

/**
 * Copyright by michidk
 * Date: 22.12.13
 * Time: 22:32
 */
public class MessageUtil
{

    private static Pattern colorPattern = Pattern.compile("&([0-9a-fk-or])");

    public static void broadcastPlayerCentered(Player player, String message)
    {
        broadcastPlayerCentered(player, message, true);
    }

    public static void broadcastPlayerCentered(Player player, String message, boolean prefix)
    {
        String[] msg = { message };
        broadcastPlayerCentered(player, msg, prefix);
    }

    public static void broadcastPlayerCentered(Player player, String[] message)
    {
        broadcastPlayerCentered(player, message, true);
    }

    public static void broadcastPlayerCentered(Player player, String[] message, boolean prefix)
    {
        if (message.length < 9)
        {
            broadcastEmptyLines(player, (int) Math.ceil(((10 - message.length) / 2d)));
            for (String line : message)
            {
                player.sendMessage(line);
            }
            broadcastEmptyLines(player, (int) Math.floor(((10 - message.length) / 2d)));
        }
        else
            for (String line : message)
                player.sendMessage(line);
    }

    public static void broadcastEmptyLines(Player player, int count)
    {
        for (int i = 0; i < count; i++)
            player.sendMessage("");
    }


    public static String parseColors(String message)
    {
        return colorPattern.matcher(message).replaceAll("\u00a7$1");
    }

    public static String parseEnumName(String enumName)
    {
        if (enumName.contains("_"))
        {
            String[] split = enumName.split("_");

            StringBuilder sb = new StringBuilder();
            for (String spl : split)
            {
                sb.append(spl.substring(0, 1) + spl.substring(1).toLowerCase() + " ");
            }

            return sb.toString().substring(0, sb.length() - 1);
        }

        return enumName.substring(0, 1) + enumName.substring(1).toLowerCase();
    }

    /**
     * Trims the string to the given maxLength if it's length is above it.
     *
     * @param stringToTrim - The string to trim to maxLength
     * @param maxLength - Maximum length of the string
     * @return The trimmed string
     */
    public static String trimString(String stringToTrim, int maxLength)
    {
        return stringToTrim.length() <= maxLength ? stringToTrim : stringToTrim.substring(0, maxLength);
    }

}
