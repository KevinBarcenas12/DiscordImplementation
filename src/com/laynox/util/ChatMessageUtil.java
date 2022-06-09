/*-
 * LICENSE
 * DiscordSRV
 * -------------
 * Copyright (C) 2016 - 2021 Austin "Scarsz" Shapiro
 * -------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * END
 */

package github.scarsz.discordsrv.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChatMessageUtil {
    public static class StaffChat {
        private final static FileConfiguration config = ConfigManagerUtil.Config.Get();
        public static void Message(Player sender, String message) {
            String staffMsg = config.getString("chat.staff").replace("%player%", sender.getName()).replace("%message%", message);
            for (Player user: Bukkit.getOnlinePlayers()) if (user.isOp()) user.sendMessage(LoggerUtil.Chat.Color(staffMsg));
        }
    }
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input == null ? "&1null " : input);
    }
    public static class broadcast {
        public static void global(String message) {
            for (Player user:Bukkit.getOnlinePlayers()) user.sendMessage(LoggerUtil.Chat.Color(message));
            Bukkit.getConsoleSender().sendMessage(LoggerUtil.Chat.Color(message));
        }
        public static void users(Player sender, String message) {
            String output = sender.getName() + " " + message;
            for (Player user : Bukkit.getOnlinePlayers()) user.sendMessage(LoggerUtil.Chat.Color(output));
        }
        public static void users(String message) {
            for (Player user:Bukkit.getOnlinePlayers()) user.sendMessage(LoggerUtil.Chat.Color(message));
        }
        public static void operators(String message) {
            for (Player user:Bukkit.getOnlinePlayers()) if (user.isOp()) user.sendMessage(LoggerUtil.Chat.Color(message));
        }
    }
    public static class message {
        public static void send(String message) {
            for (Player user : Bukkit.getOnlinePlayers()) user.sendMessage(LoggerUtil.Chat.Color(message));
        }
        public static void direct(Player player, String message) {
            if (message.isEmpty()) return;
            player.sendMessage(LoggerUtil.Chat.Color(message));
        }
    }
}
