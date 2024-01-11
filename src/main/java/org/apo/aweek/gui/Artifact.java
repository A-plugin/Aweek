package org.apo.aweek.gui;

import org.apo.aweek.Aweek;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Artifact implements Listener {
    Aweek aweek=Aweek.Instance;
    public Inventory ArtInv(Player p) {
        Inventory inventory = Bukkit.createInventory(p, 9, "아티팩트");
        return inventory;
    }

    public void ArtOpen(Player p) { //아이템이 안불러와짐
        Inventory inventory = ArtInv(p);
        List<String> playerArt = aweek.getConfig().getStringList(p.getName() + "Art");
        if (!playerArt.isEmpty()){
            for (String Art : playerArt) {
                Material material = Material.matchMaterial(Art);
                if (material != null) {
                    ItemStack itemStack = new ItemStack(material);
                    inventory.addItem(itemStack);
                }
            }
        }

        p.openInventory(inventory);
    }



    @EventHandler
    public void SaveArt(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("아티팩트")) {
            try {
                ItemStack N1 = e.getInventory().getItem(0);
                ItemStack N2 = e.getInventory().getItem(1);
                ItemStack N3 = e.getInventory().getItem(2);
                ItemStack N4 = e.getInventory().getItem(3);
                ItemStack N5 = e.getInventory().getItem(4);
                ItemStack N6 = e.getInventory().getItem(5);
                ItemStack N7 = e.getInventory().getItem(6);
                ItemStack N8 = e.getInventory().getItem(7);
                ItemStack N9 = e.getInventory().getItem(8);

                List<String> playerArt = new ArrayList<>();
                if (N1 != null) playerArt.add(N1.toString());
                if (N2 != null) playerArt.add(N2.toString());
                if (N3 != null) playerArt.add(N3.toString());
                if (N4 != null) playerArt.add(N4.toString());
                if (N5 != null) playerArt.add(N5.toString());
                if (N6 != null) playerArt.add(N6.toString());
                if (N7 != null) playerArt.add(N7.toString());
                if (N8 != null) playerArt.add(N8.toString());
                if (N9 != null) playerArt.add(N9.toString());

                aweek.getConfig().set(p.getName() + "Art", playerArt);
                aweek.saveConfig();
            } catch (Exception ex) {
                ex.printStackTrace();
                p.sendMessage("An error occurred while saving artifacts.");
            }
        }
    }

}
