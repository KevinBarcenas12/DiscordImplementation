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

package github.scarsz.discordsrv.listeners;

import github.scarsz.discordsrv.util.ChatMessageUtil;
import github.scarsz.discordsrv.util.ConfigManagerUtil;
import github.scarsz.discordsrv.util.LoggerUtil;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @SuppressWarnings("deprecation") // legacy
    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (DiscordSRV.config().getBooleanElse("UseModernPaperChatEvent", false)
                && DiscordSRV.getPlugin().isModernChatEventAvailable()) {
            return;
        }

        String message = event.getMessage();
        org.bukkit.entity.Player sender = event.getPlayer();
        FileConfiguration Config = ConfigManagerUtil.Config.Get(), players = ConfigManagerUtil.Player.Get();

        if (players.getBoolean(sender.getUniqueId()+".staffchat")) {
            ChatMessageUtil.StaffChat.Message(sender, message);
            event.setCancelled(true);
            return;
        }
        if (message.startsWith("# ") && sender.isOp()) {
            ChatMessageUtil.StaffChat.Message(sender, message.replace("# ", ""));
            event.setCancelled(true);
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(DiscordSRV.getPlugin(), () ->
                DiscordSRV.getPlugin().processChatMessage(
                        event.getPlayer(),
                        event.getMessage(),
                        DiscordSRV.getPlugin().getOptionalChannel("global"),
                        false
                )
        );

        String playerName = sender.isOp() ? Config.getString("chat.op-prefix")+sender.getName():sender.getName();
        String Style = Config.getString("chat.style"); assert Style != null;
        String Output = Style.replace("%player%", playerName).replace("%message%", message);

        event.setMessage("");
        for (org.bukkit.entity.Player user: Bukkit.getOnlinePlayers()) if (!players.getBoolean(user.getUniqueId()+".muted-users."+ sender.getUniqueId())) {
            user.sendMessage(LoggerUtil.Chat.Color(Output));
        }
        event.setMessage(message);
        LoggerUtil.console.log(LoggerUtil.Chat.Color(Output));
        event.setCancelled(true);
    }

}
