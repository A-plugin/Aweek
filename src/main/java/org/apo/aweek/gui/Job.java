package org.apo.aweek.gui;

import org.apo.aweek.Aweek;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

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
        ItemStack Ass=new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta A=Ass.getItemMeta();
        A.setDisplayName("암살자");
        A.setCustomModelData(4);
        Ass.setItemMeta(A);
        ItemStack Mas=new ItemStack(Material.STICK);
        ItemMeta Ms=Mas.getItemMeta();
        Ms.setDisplayName("마법사");
        Ms.setCustomModelData(1);
        Mas.setItemMeta(Ms);

        i.setItem(0,Sword);
        i.setItem(1,Bow);
        i.setItem(2,Tank);
        i.setItem(3, Ass);
        i.setItem(4,Mas);
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
                    case 3:
                        config.set(p.getName()+".job", "assassin");
                        aweek.saveConfig();
                        p.sendMessage(ChatColor.GREEN+"암살자를 선택하셨습니다.");
                        JobSetting(p,"assassin");
                        break;
                    case 4:
                        config.set(p.getName()+".job", "wizard");
                        aweek.saveConfig();
                        p.sendMessage("마법사를 선택했습니다.");
                        JobSetting(p,"wizard");
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
                M1.setLore(Arrays.asList("우클릭으로 대쉬를 사용할 수 있다.","쿨타임 15s"));
                M1.setUnbreakable(true);
                Sword.setItemMeta(M1);
                p.getInventory().addItem(Sword);
                break;
            case "archer":
                ItemStack Bow=new ItemStack(Material.BOW);
                ItemMeta M2=Bow.getItemMeta();
                M2.setDisplayName(ChatColor.DARK_GRAY+"궁수의 활");
                M2.setLore(Arrays.asList("좌클릭으로 화살을 더 빠르고 강력하게 쏠 수 있다.","쿨타임: 30s"));
                M2.setUnbreakable(true);
                Bow.setItemMeta(M2);
                p.getInventory().addItem(Bow);
                p.setMaxHealth(10);
                break;
            case "tank":
                ItemStack Sh=new ItemStack(Material.SHIELD);
                ItemMeta M3= Sh.getItemMeta();
                M3.setDisplayName(ChatColor.DARK_GRAY+"탱커의 방패");
                M3.setLore(Arrays.asList("받은 공격데미지를 5배로 튕겨냅니다.","[왼손들기를 해야지 발동됩니다.] 쿨다임: 1m 30s"));
                M3.setUnbreakable(true);
                Sh.setItemMeta(M3);
                p.getInventory().addItem(Sh);
                p.setMaxHealth(40);
                break;
            case "assassin":
                ItemStack As=new ItemStack(Material.NETHERITE_SWORD);
                ItemMeta M4= As.getItemMeta();
                M4.setDisplayName(ChatColor.DARK_GRAY+"암살자의 단검");
                M4.setLore(Arrays.asList("모습을 숨김니다(8s)","쿨타임: 30s"));
                M4.setUnbreakable(true);
                M4.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),"assassin", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                M4.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),"ass", 5.0, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HAND));
                As.setItemMeta(M4);

                ItemStack Ass=new ItemStack(Material.ARROW);
                ItemMeta M4_1 = Ass.getItemMeta();
                M4_1.setDisplayName(ChatColor.GRAY+"비수");
                M4_1.setCustomModelData(1);
                M4_1.setLore(Arrays.asList("4의 데미지를 입히는 비수?를 발사한다", "쿨타임: 10s"));
                Ass.setItemMeta(M4_1);

                p.getInventory().addItem(As);
                p.getInventory().addItem(Ass);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Job);
        }
    }
}
