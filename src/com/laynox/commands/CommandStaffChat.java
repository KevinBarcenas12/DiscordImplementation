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

package github.scarsz.discordsrv.commands;

import github.scarsz.discordsrv.util.ConfigManagerUtil;
import github.scarsz.discordsrv.util.LoggerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandStaffChat {

    @Command(commandNames = { "staffchat", "sc" },
            helpMessage = "Enables operator-only chat.",
            permission = "discordsrv.socialspy"
    )
    public static void execute(CommandSender _sender, String[] args) {
        FileConfiguration config = ConfigManagerUtil.Config.Get();
        FileConfiguration players = ConfigManagerUtil.Player.Get();
        if (!(_sender instanceof org.bukkit.entity.Player)) return;
        org.bukkit.entity.Player sender = (org.bukkit.entity.Player) _sender;
        if (!sender.hasPermission("discordsrv.staffchat")) {
            sender.sendMessage(LoggerUtil.Chat.Color(config.getString("messages.noPerms")));
            return;
        }
        if (!players.getBoolean(sender.getName()+".staffchat")) {
            players.set(sender.getName()+".staffchat", true);
            sender.sendMessage(LoggerUtil.Chat.Color("&5StaffChat &6activado."));
        } else {
            players.set(sender.getName()+".staffchat", false);
            sender.sendMessage(LoggerUtil.Chat.Color("&5StaffChat &6desactivado."));
        }
        ConfigManagerUtil.Player.Save();
    }
}
