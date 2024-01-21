package org.apo.aweek.system;

import org.apo.aweek.Aweek;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Artifact implements Listener {
    Aweek aweek = Aweek.Instance;

    public Inventory ArtInv(Player p) {
        File file = new File(aweek.getDataFolder() + "/Artifact", p.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("artifact");
        Inventory inventory= Bukkit.createInventory(p,9,"아티팩트");
        if (section != null) {
            for (String slot : section.getKeys(false)) {
                ItemStack itemStack = section.getItemStack(slot);
                if (itemStack != null) {
                    inventory.setItem(Integer.parseInt(slot), itemStack);
                }
            }
        }

        return inventory;
    }


    public void ArtOpen(Player p) {
        Inventory inventory = ArtInv(p);
        if (inventory!=null){
            p.openInventory(inventory);
        }
    }

    @EventHandler
    public void Click(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("아티팩트")) {
            Player p= (Player) e.getWhoClicked();
            if (e.getCurrentItem()!=null) {
                String n=e.getCurrentItem().getItemMeta().getDisplayName();
                if (!n.contains("ARTIFACT")) {
                    e.setCancelled(true);
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 1.0F, 1.6F);
                }
            }
        }
    }


    @EventHandler
    public void Close(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals("아티팩트")) {
            Player p = (Player) e.getPlayer();
            File file = new File(aweek.getDataFolder() + "/Artifact", p.getUniqueId() + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            ItemStack[] contents = e.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] != null) {
                    config.set("artifact." + i, contents[i]);
                } else if (contents[i]==null) {
                    config.set("artifact."+i, null);
                }
            }

            try {
                config.save(file);
            } catch (IOException er) {
                er.printStackTrace();
            }

            if (ArtT(p).contains("HP")){
                if (ArtV(p, "HP") != 0) {
                    int Mp = aweek.getConfig().getInt(p.getName()+".MaxHp");
                    int Ap = ArtV(p, "HP");
                    p.setMaxHealth(Mp + Ap);
                }
                else {
                    p.setMaxHealth(aweek.getConfig().getInt(p.getName() + ".MaxHp"));
                }
            } else {
                p.setMaxHealth(aweek.getConfig().getInt(p.getName() + ".MaxHp"));
            }
        }
    }

    public void ArtE(Player p) { //아티팩트 효과
        new BukkitRunnable() {
            @Override
            public void run() {
                File file = new File(aweek.getDataFolder() + "/Artifact", p.getUniqueId() + ".yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                ConfigurationSection section = config.getConfigurationSection("artifact");
                if (section != null) {
                    for (String slot : section.getKeys(false)) {
                        ItemStack itemStack = section.getItemStack(slot);
                        ItemMeta itemMeta=itemStack.getItemMeta();
                        if (itemStack != null) {
                        }
                    }
                }
            }
        }.runTaskTimer(aweek,0L, 1L);
    }

    public void art(Player p, int v) { //ATC, SPD, HP
        ItemStack itemStack=new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta itemMeta=itemStack.getItemMeta();
        itemMeta.setCustomModelData(1);
        itemMeta.setDisplayName("§cATK ARTIFACT");
        itemMeta.setLore(Arrays.asList("+"+v));
        itemStack.setItemMeta(itemMeta);
        p.getInventory().addItem(itemStack);
        itemMeta.setCustomModelData(2);
        itemMeta.setDisplayName("§9SPD ARTIFACT");
        itemMeta.setLore(Arrays.asList("+"+v));
        itemStack.setItemMeta(itemMeta);
        p.getInventory().addItem(itemStack);
        itemMeta.setCustomModelData(1);
        itemMeta.setDisplayName("§aHP ARTIFACT");
        itemMeta.setLore(Arrays.asList("+"+v));
        itemStack.setItemMeta(itemMeta);
        p.getInventory().addItem(itemStack);
    }

    public List<String> ArtT(Player p) {
        List<String> artifactTypes = new ArrayList<>();

        File file = new File(aweek.getDataFolder() + "/Artifact", p.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("artifact");

        if (section != null) {
            for (String slot : section.getKeys(false)) {
                ItemStack itemStack = section.getItemStack(slot);
                if (itemStack != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta.getDisplayName().contains("ARTIFACT")) {
                        String Dn = itemMeta.getDisplayName();
                        if (Dn.contains("§c")) {
                            artifactTypes.add("ATK");
                        } else if (Dn.contains("§9")) {
                            artifactTypes.add("SPD");
                        } else if (Dn.contains("§a")) {
                            artifactTypes.add("HP");
                        }
                    }
                }
            }
        }
        return artifactTypes;
    }

    public int ArtV(Player p, String ty) {
        File file = new File(aweek.getDataFolder() + "/Artifact", p.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("artifact");

        int Atv = 0;
        int Stv = 0;
        int Htv = 0;
        if (section != null) {
            for (String slot : section.getKeys(false)) {
                ItemStack itemStack = section.getItemStack(slot);
                if (itemStack != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta.getDisplayName().contains("ARTIFACT")) {
                        String Lore = itemMeta.getLore().toString();
                        Lore = Lore.replace("+", "");
                        String numericPart = Lore.replaceAll("[^0-9]", "");
                        if (!numericPart.isEmpty()) {
                            try {
                                int v = Integer.parseInt(numericPart);
                                    if (itemMeta.getDisplayName().contains("§c")) {
                                        Atv += v;
                                    } else if (itemMeta.getDisplayName().contains("§9")) {
                                        Stv += v;
                                    } else if (itemMeta.getDisplayName().contains("§a")) {
                                        Htv += v;
                                    }
                            } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        if (ty.equalsIgnoreCase("ATK")) {
            return Atv;
        } else if (ty.equalsIgnoreCase("SPD")) {
            return Stv;
        } else if (ty.equalsIgnoreCase("HP")) {
            return Htv;
        } else {
            return 0;
        }
    }


    @EventHandler
    public void ATK(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof  Player) {
            if (ArtT((Player) e.getDamager()).contains("ATK")) {
                if (ArtV((Player) e.getDamager(), "ATK") != 0) {
                    double damage = e.getDamage();
                    double damage2 = ArtV(((Player) e.getDamager()).getPlayer(),"ATK");
                    e.setDamage(damage + damage2);
                    e.getDamager().sendMessage("DA"+damage2); // DAMAGE TEST
                }
            }
        }
        e.getDamager().sendMessage(String.valueOf(e.getDamage())); // DAMAGE TEST 2
    }

    @EventHandler
    public void SDP(PlayerMoveEvent e) {
        Player p=e.getPlayer();
        if (ArtT(p).contains("SPD")){
            if (ArtV(p, "SPD") != 0) {
                float spd = Float.parseFloat("0." + (ArtV(p, "SPD")) + "F");
                float wsp = p.getWalkSpeed();
                if ((spd + wsp) > 1F) {
                    p.setWalkSpeed(1F);
                } else if ((spd + wsp) < (-1F)) {
                    p.setWalkSpeed(-1F);
                } else {
                    p.setWalkSpeed(spd + wsp);
                }
            } else {
                p.setWalkSpeed(0.2F);
            }
        } else {
            p.setWalkSpeed(0.2F);
        }
    }
}
