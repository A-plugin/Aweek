package org.apo.aweek.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import org.apo.aweek.Aweek;
import org.apo.aweek.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class scoreboard implements Listener {
    Aweek aweek=Aweek.Instance;
    Data data=new Data();
    FileConfiguration config= aweek.getConfig();
    public void CreateScoreboard(Player p) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = scoreboard.registerNewObjective("WeekScore", "dummy", ChatColor.YELLOW + "A WEEK");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.getScore(ChatColor.AQUA + "환영합니다!").setScore(11);
        o.getScore(ChatColor.LIGHT_PURPLE + "플레이어 " +ChatColor.WHITE+p.getName()).setScore(10);
        int H= (int) p.getHealth();
        o.getScore(ChatColor.GREEN + "HP: " + H).setScore(9);
        o.getScore(ChatColor.DARK_AQUA+"Lv."+aweek.getConfig().getString(p.getName()+".Lv")).setScore(8);
        o.getScore(ChatColor.GOLD+"직업: "+ChatColor.GRAY+config.get(p.getName()+".job")).setScore(7);
        o.getScore(ChatColor.DARK_GREEN+"돈: "+ PlaceholderAPI.setPlaceholders(p,data.money)+"$").setScore(6);
        p.setScoreboard(scoreboard);
        config.set(p.getName()+".scoreb", true);
        aweek.saveConfig();
    }

    public void RlS(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                CreateScoreboard(p);
            }
        }.runTaskTimer(aweek, 2,2L);
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        RlS(e.getPlayer());
    }
}
