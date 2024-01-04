package org.apo.aweek.Commands;

import org.apo.aweek.boss.Mon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class aweekC implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p= ((Player) sender);
            if (p.hasPermission("Aweek.aweek")) {
                if (args[0].equals("M")){
                    Mon mon = new Mon();
                    mon.spawnB(p);
                }
            }
            return true;
        }
        return false;
    }
}
