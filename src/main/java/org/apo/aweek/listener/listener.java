package org.apo.aweek.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.apo.aweek.Aweek;
import org.apo.aweek.gui.Job;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class listener implements Listener {
    Job j=new Job();
    Aweek aweek=Aweek.Instance;
    FileConfiguration config= aweek.getConfig();
    scoreboard scoreboard=new scoreboard();
    @EventHandler
    public void Join(PlayerJoinEvent e) {
        Player p=e.getPlayer();
        // if (!p.hasPlayedBefore()) {
            p.openInventory(j.JobInv(p));
        // }
        if (config.get(p.getName()+".score")!=null) {
            if (config.getBoolean(p.getName()+".scoreb")) {
                scoreboard.RlS(p);
            }
        }
        config.set(p.getName()+".uuid", p.getUniqueId());
    }

    @EventHandler
    public void Event(PlayerInteractEvent e) {
        Player p=e.getPlayer();
        Action a=e.getAction();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String displayName = item.getItemMeta().getDisplayName();
                if (displayName.equals(ChatColor.DARK_GRAY + "전사의 검") && item.getType().equals(Material.IRON_SWORD)) {
                    if (p.getCooldown(item.getType())==0){
                        p.setVelocity(p.getLocation().getDirection().multiply(1.5));
                        p.setCooldown(Material.IRON_SWORD, 15 * 20);
                    }
                }
                if (displayName.equals(ChatColor.DARK_GRAY+"암살자의 단검")&& item.getType().equals(Material.NETHERITE_SWORD)) {
                    if (p.getCooldown(item.getType())==0){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 8 * 20, 5 * 20), true);
                        p.setCooldown(Material.NETHERITE_SWORD, 15 * 20 * 2);
                    }
                }
            }
        } else if (a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_BLOCK)) {
            if (item!=null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
                String displayName = item.getItemMeta().getDisplayName();
                if (displayName.equalsIgnoreCase(ChatColor.DARK_GRAY + "궁수의 활") && item.getType().equals(Material.BOW)) {
                    if (p.getCooldown(item.getType()) == 0) {
                        if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                            if (p.getInventory().contains(Material.ARROW)) {
                                Arrow arrow = p.launchProjectile(Arrow.class, p.getLocation().getDirection().multiply(3));
                                arrow.setShooter(p);
                                ItemStack arrows = p.getInventory().getItem(p.getInventory().first(Material.ARROW));
                                if (arrows.getAmount() == 1) {
                                    p.getInventory().remove(arrows);
                                } else {
                                    arrows.setAmount(arrows.getAmount() - 1);
                                }
                                p.setCooldown(Material.BOW, 30 * 20);
                            }
                        } else {
                            Arrow arrow = p.launchProjectile(Arrow.class, p.getLocation().getDirection().multiply(3));
                            arrow.setShooter(p);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void Chat(PlayerChatEvent e) {
        Player p=e.getPlayer();
        e.setCancelled(true);
        Bukkit.broadcastMessage(ChatColor.GRAY+p.getName()+ChatColor.AQUA+" >> "+ChatColor.WHITE+e.getMessage());
    }

    @EventHandler
    public void Tank(EntityDamageByEntityEvent e) {
        Entity entity=e.getDamager();
        if (e.getEntity() instanceof Player) {
            Player p= (Player) e.getEntity();
            if (p.isBlocking()) {
                ItemStack item=p.getInventory().getItemInOffHand();
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GRAY+"탱커의 방패")){
                    if (p.getCooldown(Material.SHIELD)==0){
                        if (entity instanceof LivingEntity){
                            e.setCancelled(true);
                            ((LivingEntity) entity).damage(e.getDamage()*5);
                            if (!p.getGameMode().equals(GameMode.CREATIVE)){
                                p.setCooldown(Material.SHIELD, 90 * 20);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void Test(PlayerJumpEvent e) {
        Player p=e.getPlayer();
        if (aweek.economy !=null){
            if (!p.isSneaking()){
                aweek.economy.depositPlayer(p, 20.0);
            } else {
                aweek.economy.withdrawPlayer(p,10.0);
            }
        }
    }

}
