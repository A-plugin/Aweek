package org.apo.aweek.gui;

import org.apo.aweek.Aweek;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Artifact implements Listener {
    Aweek aweek=Aweek.Instance;
    public Inventory ArtInv(Player p) {
        Inventory inventory = Bukkit.createInventory(p, 9, "아티팩트");
        return inventory;
    }

    public void ArtOpen(Player p) {
        Inventory inventory = ArtInv(p);
        String playerName = p.getName();
        File file = new File(aweek.getDataFolder() + "/Artifact", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> playerArtStrings = config.getStringList("Art");

        if (!playerArtStrings.isEmpty()){
            for (String itemString : playerArtStrings) {
                try {
                    ItemStack itemStack = new ItemStack(Material.valueOf(itemString)); //null

                    if (itemStack != null) {
                        inventory.addItem(itemStack);
                        inventory.setContents(new ItemStack[]{itemStack});
                    } else {
                        p.sendMessage("ItemStack is Null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    p.sendMessage(String.valueOf(e));
                }
            }
        }else {
            p.sendMessage("EMANUIL:");
        }

        p.openInventory(inventory);
    }


    @EventHandler
    public void Close(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals("아티팩트")) {
            Player p = (Player) e.getPlayer();
            Inventory inventory = e.getInventory();
            List<String> playerArt = new ArrayList<>();
            File file = new File(aweek.getDataFolder() + "/Artifact", p.getName() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (ItemStack item : inventory.getContents()) {
                if (item != null && !item.getType().equals(Material.AIR)) {
                    playerArt.add(item.getType().name());
                }
            }

            config.set("Art.m", playerArt);
            try {
                config.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
