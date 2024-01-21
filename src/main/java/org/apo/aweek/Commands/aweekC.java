package org.apo.aweek.Commands;

import org.apo.aweek.boss.Mon;
import org.apo.aweek.boss.TUE;
import org.apo.aweek.system.Artifact;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class aweekC implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p= ((Player) sender);
            if (p.hasPermission("Aweek.aweek")) {
                if (args[0].equals("M")){
                    Mon mon = new Mon();
                    mon.spawnB(p);
                }
                if (args[0].equals("Tue")){
                    TUE tue=new TUE();
                    tue.spawnB(p);
                }
                if (args[0].equals("Mi")) {
                    ItemStack i=new ItemStack(Material.PHANTOM_MEMBRANE);
                    ItemMeta itemMeta=i.getItemMeta();
                    itemMeta.setCustomModelData(1);
                    itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE +"월요일의 가벼운 날개");
                    i.setItemMeta(itemMeta);
                    p.getInventory().addItem(i);
                }
                if (args[0].equals("A")) {
                    Artifact artifact=new Artifact();
                    artifact.art(p, Integer.parseInt(args[1]));
                }
            }
            return true;
        }
        return false;
    }
}
