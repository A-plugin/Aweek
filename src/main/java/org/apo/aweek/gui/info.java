package org.apo.aweek.gui;

import me.clip.placeholderapi.PlaceholderAPI;
import org.apo.aweek.Aweek;
import org.apo.aweek.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class info implements Listener {
    Data data=new Data();
    Aweek aweek=Aweek.Instance;
    FileConfiguration config= aweek.getConfig();
    public void Open(Player p) {
        p.openInventory(InfoInv(p));
    }

    public Inventory InfoInv(Player p) {
        Inventory i= Bukkit.createInventory(p,9*5,"정보");
        ItemStack PlayerH=new ItemStack(Material.PLAYER_HEAD);
        SkullMeta Ph=(SkullMeta) PlayerH.getItemMeta();
        Ph.setOwningPlayer(p);
        Ph.setDisplayName(p.getName());
        PlayerH.setItemMeta(Ph);
        i.setItem(13,PlayerH);
        ItemStack Money=new ItemStack(Material.EMERALD);
        ItemMeta PMeta=Money.getItemMeta();
        PMeta.setDisplayName(ChatColor.GREEN +"Money: "+ PlaceholderAPI.setPlaceholders(p,data.money)+"$");
        Money.setItemMeta(PMeta);
        i.setItem(19,Money);
        ItemStack Job=new ItemStack(Material.IRON_SWORD);
        ItemMeta J= Job.getItemMeta();
        J.setDisplayName(ChatColor.GOLD+"직업: §f"+config.get(p.getName()+".job"));
        Job.setItemMeta(J);
        i.setItem(20, Job);
        return i;
    }

    @EventHandler
    public void MenuClick(InventoryClickEvent e) {
        Player p= (Player) e.getWhoClicked();
        Inventory in=e.getClickedInventory();
        if(e.getView().getTitle().equalsIgnoreCase("정보")) {
            e.setCancelled(true);
            if (e.isLeftClick()){

            }
        }
    }
}
