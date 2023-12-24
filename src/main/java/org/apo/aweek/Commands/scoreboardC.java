package org.apo.aweek.Commands;

import org.apo.aweek.listener.scoreboard;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class scoreboardC implements CommandExecutor {
    scoreboard scoreboard=new scoreboard();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("on")){
                scoreboard.RlS(p);
                p.sendMessage(ChatColor.GREEN+"스코어보드를 켰습니다.");

            } else if (args[0].equalsIgnoreCase("off")){
                scoreboard.DisS((Player) sender);
                p.sendMessage(ChatColor.RED+"스코어보드를 껐습니다.");
            }
            return true;
        }
        sender.sendMessage("플레이어만 사용가능 합니다.");
        return false;
    }
}
