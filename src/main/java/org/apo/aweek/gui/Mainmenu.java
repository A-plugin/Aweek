package org.apo.aweek.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Mainmenu implements Listener {
    org.apo.aweek.gui.info info=new info();
    @EventHandler
    public void Open(PlayerSwapHandItemsEvent e) {
        Player p=e.getPlayer();
        if (p.isSneaking()) {
            e.setCancelled(true);
            p.openInventory(menuInv(p));
        }
    }
    public Inventory menuInv(Player p) {
        Inventory i= Bukkit.createInventory(p, 9*3, "메뉴");
        ItemStack BlankSlot=new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta BS=BlankSlot.getItemMeta();
        BS.setDisplayName(" ");
        BlankSlot.setItemMeta(BS);
        ItemStack Info=new ItemStack(Material.PLAYER_HEAD);
        SkullMeta InfoM=(SkullMeta)Info.getItemMeta();
        InfoM.setOwningPlayer(p);
        InfoM.setDisplayName("정보 보기");
        Info.setItemMeta(InfoM);
        i.setItem(0, BlankSlot);
        i.setItem(1,BlankSlot);
        i.setItem(2,BlankSlot);
        i.setItem(3,BlankSlot);
        i.setItem(4,BlankSlot);
        i.setItem(5,BlankSlot);
        i.setItem(7,BlankSlot);
        i.setItem(8,BlankSlot);
        i.setItem(6,BlankSlot);
        i.setItem(9,BlankSlot);
        i.setItem(17,BlankSlot);
        i.setItem(10, Info);
        return i;
    }
    @EventHandler
    public void MenuClick(InventoryClickEvent e) {
        Player p= (Player) e.getWhoClicked();
        Inventory in=e.getClickedInventory();

        if(e.getView().getTitle().equalsIgnoreCase("메뉴")) {
            if (e.isLeftClick()){
                e.setCancelled(true);
                if (e.getSlot() == 10) {
                    p.closeInventory();
                    info.Open(p);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }
}
