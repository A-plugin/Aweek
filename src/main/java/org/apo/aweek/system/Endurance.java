package org.apo.aweek.system;

import org.apo.aweek.Aweek;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class Endurance implements Listener {
    private BossBar bar;
    Aweek aweek= Aweek.Instance;
    public double E;
    public void bar() {
        bar = Bukkit.createBossBar("체력", BarColor.RED, BarStyle.SOLID);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.isSprinting()) {
                        bar.addPlayer(player);
                        double progress = bar.getProgress();
                        E = progress;
                        if (progress > 0) {
                            double newProgress = progress - 0.05;
                            bar.setProgress(Math.max(newProgress, 0.0));
                        } else {
                            player.setSprinting(false);
                        }
                    } else {
                        if (bar.getProgress()==1){
                            bar.removePlayer(player);
                        }
                        double progress = bar.getProgress();
                        E = progress;
                        if (progress < 1) {
                            double newProgress = progress + 0.025;
                            bar.setProgress(Math.min(newProgress, 1.0));
                        }
                    }
                }
            }

        }.runTaskTimer(aweek, 0L, 20L);
    }
}
