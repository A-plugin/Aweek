package org.apo.aweek.gui;

import org.apo.aweek.Aweek;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

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


}
