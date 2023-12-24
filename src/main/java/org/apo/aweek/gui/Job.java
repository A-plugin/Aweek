package org.apo.aweek.gui;

import org.apo.aweek.Aweek;
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

import java.util.Collections;

public class Job implements Listener {
    public Inventory JobInv(Player p) {
        Inventory i= Bukkit.createInventory(p,9,"직업 선택");
        ItemStack Sword=new ItemStack(Material.IRON_SWORD);
        ItemMeta S=Sword.getItemMeta();
        S.setDisplayName("검사");
        Sword.setItemMeta(S);
        ItemStack Bow=new ItemStack(Material.BOW);
        ItemMeta B=Bow.getItemMeta();
        B.setDisplayName("궁수");
        Bow.setItemMeta(B);
        ItemStack Tank=new ItemStack(Material.SHIELD);
        ItemMeta T= Tank.getItemMeta();
        T.setDisplayName("탱커");
        Tank.setItemMeta(T);

        i.setItem(0,Sword);
        i.setItem(1,Bow);
        i.setItem(2,Tank);
        return i;
    }
    Aweek aweek=Aweek.Instance;
    FileConfiguration config= aweek.getConfig();

    @EventHandler
    public void JobClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory in = e.getClickedInventory();
        if (e.getView().getTitle().equalsIgnoreCase("직업 선택")) {
            e.setCancelled(true);
            if (e.isLeftClick()) {
                p.closeInventory();
                switch (e.getSlot()) {
                    case 0:
                        p.sendMessage(ChatColor.GREEN + "전사를 선택하셨습니다.");
                        config.set(p.getName()+".job","warrior");
                        aweek.saveConfig();
                        JobSetting(p, "warrior");
                        break;
                    case 1:
                        config.set(p.getName()+".job", "archer");
                        aweek.saveConfig();
                        p.sendMessage(ChatColor.GREEN + "궁수를 선택하셨습니다.");
                        JobSetting(p, "archer");
                        break;
                    case 2:
                        config.set(p.getName()+".job", "tank");
                        aweek.saveConfig();
                        p.sendMessage(ChatColor.GREEN+"탱커를 선택하셨습니다.");
                        JobSetting(p,"tank");
                        break;
                    default:
                        p.sendMessage(ChatColor.RED + "존재하지 않는 직업입니다.");
                        JobInv(p);
                }
            }
        }
    }

    public void JobSetting(Player p, String Job) {
        switch (Job) {
            case "warrior":
                ItemStack Sword=new ItemStack(Material.IRON_SWORD);
                ItemMeta M1=Sword.getItemMeta();
                M1.setDisplayName(ChatColor.DARK_GRAY+"전사의 검");
                M1.setLore(Collections.singletonList("우클릭으로 대쉬를 사용할 수 있다. (쿨타임 15s)"));
                M1.setUnbreakable(true);
                Sword.setItemMeta(M1);
                p.getInventory().addItem(Sword);
                break;
            case "archer":
                ItemStack Bow=new ItemStack(Material.BOW);
                ItemMeta M2=Bow.getItemMeta();
                M2.setDisplayName(ChatColor.DARK_GRAY+"궁수의 활");
                M2.setLore(Collections.singletonList("좌클릭으로 화살을 더 빠르고 강력하게 쏠 수 있다. (쿨타임: 30s)"));
                M2.setUnbreakable(true);
                Bow.setItemMeta(M2);
                p.getInventory().addItem(Bow);
                p.setMaxHealth(10);
                break;
            case "tank":
                ItemStack Sh=new ItemStack(Material.SHIELD);
                ItemMeta M3= Sh.getItemMeta();
                M3.setDisplayName(ChatColor.DARK_GRAY+"탱커의 방패");
                M3.setLore(Collections.singletonList("받은 공격데미지를 5배로 튕겨냅니다. (쿨다임: 1m 30s)"));
                M3.setUnbreakable(true);
                Sh.setItemMeta(M3);
                p.getInventory().addItem(Sh);
                p.setMaxHealth(40);
                break;
        }
    }
}
