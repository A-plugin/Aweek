package org.apo.aweek.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JokeC implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p= (Player) sender;
            if (p.hasPermission("Aweek.joke")){
                if (args[0].equalsIgnoreCase("demo")) {
                    if (args[1] != null) {
                        Player pl = Bukkit.getPlayer(args[1]);
                        if (pl != null) {
                            pl.showDemoScreen();
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
