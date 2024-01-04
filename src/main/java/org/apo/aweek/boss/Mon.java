package org.apo.aweek.boss;

import org.apo.aweek.Aweek;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Mon implements Listener {
    Aweek aweek=Aweek.Instance;
    public void spawnB(Player p) {
        p.getWorld().spawn(p.getLocation().add(0,20,0), Phantom.class,Phantom -> {
            Phantom.setSize(20);
            Phantom.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999999*20,255));
            Phantom.setMaxHealth(200);
            Phantom.setHealth(9);
            Phantom.setShouldBurnInDay(false);
            Phantom.addScoreboardTag("Mon");

            BossBar bossBar=Bukkit.createBossBar("Illusion(MonDay)", BarColor.RED, BarStyle.SEGMENTED_20);
            bossBar.setVisible(true);
            bossBar.setProgress((double) Phantom.getHealth() / Phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            for (Player player: p.getWorld().getPlayers()) {
                if (player.getLocation().distance(Phantom.getLocation()) <=70) {
                    bossBar.addPlayer(player);
                }
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!Phantom.isDead()) {
                        bossBar.setProgress((double) Phantom.getHealth() / Phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    } else {
                        bossBar.removeAll();
                    }
                }
            }.runTaskTimer(aweek,0L, 1L);
        });

    }

    public void MonWorld() {
        WorldCreator worldCreator=new WorldCreator("Mon");
        worldCreator.environment(World.Environment.CUSTOM);
        worldCreator.generateStructures(true);
        World world= worldCreator.createWorld();
    }

    @EventHandler
    public void Death(EntityDeathEvent e) {
        if (e.getEntity().getScoreboardTags().contains("Mon")) {
            e.setDroppedExp(200);
            e.getDrops().clear();
            ItemStack i=new ItemStack(Material.PHANTOM_MEMBRANE);
            ItemMeta itemMeta=i.getItemMeta();
            itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE +"월요일의 가벼운 날개");
            i.setItemMeta(itemMeta);
            e.getDrops().add(i);
        }
    }


    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Phantom) {
            if (e.getEntity().getScoreboardTags().contains("Mon")) {
                Bukkit.broadcastMessage(e.getDamage()+String.valueOf(e.getCause()));
                if (e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                    e.setCancelled(true);
                }

            }
        }
        e.getEntity().sendMessage(String.valueOf(e.getCause()));
    }
}
