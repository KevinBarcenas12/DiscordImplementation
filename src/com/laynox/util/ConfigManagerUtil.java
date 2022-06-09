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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigManagerUtil {
    public static class Player {
        private static File file;
        private static FileConfiguration players;
        public static void Set(Plugin plugin) {
            file = new File(plugin.getDataFolder(), File.separator + "players.yml");
            if (!file.exists()) try {
                file.createNewFile();
            } catch (IOException exception) {
                LoggerUtil.console.log("&4"+exception.getMessage());
            }
            players = YamlConfiguration.loadConfiguration(file);
        }
        public static FileConfiguration Get() {
            Save();
            return players;
        }
        public static void Save() {
            try {
                players.save(file);
            } catch (IOException exception) {
                LoggerUtil.console.log("&4"+exception.getMessage());
            }
        }
    }
    public static class Config {
        private static File file;
        private static FileConfiguration config;
        public static void Set(Plugin plugin) {
            file = new File(plugin.getDataFolder(), File.separator + "custom.yml");
            if (!file.exists()) try {
                file.createNewFile();
            } catch (IOException exception) {
                LoggerUtil.console.log("&4" + exception.getMessage());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }
        public static FileConfiguration Get() {
            Save();
            return config;
        }
        public static void Save() {
            try {
                config.save(file);
            } catch (IOException exception) {
                LoggerUtil.console.log("&4" + exception.getMessage());
            }
        }
    }
}
