package org.apo.aweek.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.apo.aweek.Aweek;
import org.apo.aweek.gui.Job;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class listener implements Listener {
    Job j=new Job();
    Aweek aweek=Aweek.Instance;
    FileConfiguration config= aweek.getConfig();
    scoreboard scoreboard=new scoreboard();
    Enchantable enchantable=new Enchantable();
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
        config.set(p.getName()+".uuid", p.getUniqueId().toString());
    }
    @EventHandler
    public void Event(PlayerInteractEvent e) {

        Player p=e.getPlayer();
        Action a=e.getAction();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (a.equals(Action.RIGHT_CLICK_AIR)) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String displayName = item.getItemMeta().getDisplayName();
                if (p.getCooldown(item.getType())==0){
                    if (displayName.equals(ChatColor.DARK_GRAY + "전사의 검") && item.getType().equals(Material.IRON_SWORD)) {
                        p.setVelocity(p.getLocation().getDirection().multiply(1.5));
                        p.setCooldown(p.getInventory().getItemInMainHand().getType(), 15 * 20);

                    }
                    if (displayName.equals(ChatColor.DARK_GRAY + "암살자의 단검") && item.getType().equals(Material.NETHERITE_SWORD)) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 8 * 20, 5 * 20), true);
                        p.setCooldown(p.getInventory().getItemInMainHand().getType(), 15 * 20 * 2);
                    }
                    if (displayName.equals(ChatColor.LIGHT_PURPLE + "월요일의 가벼운 날개")) {
                        p.setVelocity(p.getLocation().getDirection().multiply(1.7));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 2, 3));
                        p.setCooldown(p.getInventory().getItemInMainHand().getType(),20*20);
                    }
                }
            } else if (true) {

            }
        } else if (a.equals(Action.LEFT_CLICK_AIR)) {
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
    public void en(EnchantItemEvent e) {
        Player p=e.getEnchanter();
        ItemStack itemStack=e.getItem();
        if (enchantable.Boots(itemStack)) {
            if (e.getEnchantsToAdd().get(Enchantment.THORNS) == null) {
                int ran = (int)(Math.random() * 100.0 % 10.0);
                if (ran > 6) {
                    ArrayList lore;
                    ItemStack item;
                    ItemMeta meta;
                    lore = new ArrayList();
                    lore.add("§c점프의 저주");
                    item = e.getItem();
                    meta = item.getItemMeta();
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
            }
        }
        if (enchantable.sword(itemStack)) {
            int ran = (int)(Math.random() * 100.0 % 10.0);
            if (ran==9) {
                ArrayList lore;
                ItemStack item;
                ItemMeta meta;
                lore = new ArrayList();
                lore.add("§7폭발은 예술이다!");
                item = e.getItem();
                meta = item.getItemMeta();
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void Jump(PlayerJumpEvent e) {
        Player p=e.getPlayer();
        if (p.getInventory().getBoots()!=null) {
            if (p.getInventory().getBoots().getItemMeta().hasLore()) {
                if (p.getInventory().getBoots().getItemMeta().getLore().contains("§c점프의 저주")) {
                    e.setCancelled(true);
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
        if (e.getDamager() instanceof Player) {
            Player p=(Player) e.getDamager();
            if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()){
                if (p.getInventory().getItemInMainHand().getItemMeta().getLore().contains("§7폭발은 예술이다!")) {
                    Random rand = new Random();
                    int ran = rand.nextInt(100) + 1;
                    if (ran == 44) {
                        Bukkit.shutdown();
                    } else if (ran <= 9) {
                        p.getWorld().createExplosion(e.getEntity().getLocation(), 5);
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
