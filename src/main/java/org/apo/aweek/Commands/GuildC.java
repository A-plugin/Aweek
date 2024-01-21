package org.apo.aweek.Commands;

import org.apo.aweek.system.Guild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuildC implements CommandExecutor {
    Guild guild=new Guild();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p= (Player) sender;
            if (p.hasPermission("Aweek.guild")) {
                if (args.length > 0) {
                    String action = args[0];
                    if (action.equalsIgnoreCase("create")) {
                        if (args.length > 1) {
                            guild.create(args[1], p.getName(), p);
                        } else {
                            p.sendMessage("길드 이름을 입력해주세요.");
                        }
                    } else if (action.equalsIgnoreCase("join")){
                        if (args.length > 2) {
                            guild.join(args[1], args[2], p);
                        } else {
                            p.sendMessage("길드 이름과 플레이어 이름을 입력해주세요.");
                        }
                    } else if (action.equalsIgnoreCase("leave")) {
                        if (args.length > 1) {
                            guild.leave(args[1],p.getName(),p);
                        } else {
                            p.sendMessage("길드 이름을 입력해주세요.");
                        }
                    } else {
                        p.sendMessage("유효한 명령어가 아닙니다. 'create', 'join', 'leave' 중 하나를 입력해주세요.");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
