package org.apo.aweek.system;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Enchantable {
    public boolean armor(ItemStack itemStack) {
        Material material=itemStack.getType();
        if (material.equals(Material.LEATHER_BOOTS)) return true;
        if (material.equals(Material.LEATHER_LEGGINGS)) return true;
        if (material.equals(Material.LEATHER_CHESTPLATE)) return true;
        if (material.equals(Material.LEATHER_HELMET)) return true;
        if (material.equals(Material.IRON_BOOTS)) return true;
        if (material.equals(Material.IRON_LEGGINGS)) return true;
        if (material.equals(Material.IRON_CHESTPLATE)) return true;
        if (material.equals(Material.IRON_HELMET)) return true;
        if (material.equals(Material.GOLDEN_BOOTS)) return true;
        if (material.equals(Material.GOLDEN_LEGGINGS)) return true;
        if (material.equals(Material.GOLDEN_CHESTPLATE)) return true;
        if (material.equals(Material.GOLDEN_HELMET)) return true;
        if (material.equals(Material.DIAMOND_BOOTS)) return true;
        if (material.equals(Material.DIAMOND_LEGGINGS)) return true;
        if (material.equals(Material.DIAMOND_CHESTPLATE)) return true;
        if (material.equals(Material.DIAMOND_HELMET)) return true;
        if (material.equals(Material.NETHERITE_BOOTS)) return true;
        if (material.equals(Material.NETHERITE_LEGGINGS)) return true;
        if (material.equals(Material.NETHERITE_CHESTPLATE)) return true;
        if (material.equals(Material.NETHERITE_HELMET)) return true;
        return false;
    }

    public boolean Helmet(ItemStack itemStack) {
        Material material=itemStack.getType();
        if (material.equals(Material.LEATHER_HELMET)) return true;
        if (material.equals(Material.IRON_HELMET)) return true;
        if (material.equals(Material.GOLDEN_HELMET)) return true;
        if (material.equals(Material.DIAMOND_HELMET)) return true;
        if (material.equals(Material.NETHERITE_HELMET)) return true;
        return false;
    }

    public boolean Chestplate(ItemStack itemStack) {
        Material material=itemStack.getType();
        if (material.equals(Material.LEATHER_CHESTPLATE)) return true;
        if (material.equals(Material.IRON_CHESTPLATE)) return true;
        if (material.equals(Material.GOLDEN_CHESTPLATE)) return true;
        if (material.equals(Material.DIAMOND_CHESTPLATE)) return true;
        if (material.equals(Material.NETHERITE_CHESTPLATE)) return true;
        return false;
    }

    public boolean Leggings(ItemStack itemStack) {
        Material material=itemStack.getType();
        if (material.equals(Material.LEATHER_LEGGINGS)) return true;
        if (material.equals(Material.IRON_LEGGINGS)) return true;
        if (material.equals(Material.GOLDEN_LEGGINGS)) return true;
        if (material.equals(Material.DIAMOND_LEGGINGS)) return true;
        if (material.equals(Material.NETHERITE_LEGGINGS)) return true;
        return false;
    }

    public boolean Boots(ItemStack itemStack) {
        Material material=itemStack.getType();
        if (material.equals(Material.LEATHER_BOOTS)) return true;
        if (material.equals(Material.IRON_BOOTS)) return true;
        if (material.equals(Material.GOLDEN_BOOTS)) return true;
        if (material.equals(Material.DIAMOND_BOOTS)) return true;
        if (material.equals(Material.NETHERITE_BOOTS)) return true;
        return false;
    }

    public boolean Stuff(ItemStack itemStack) {
        Material material=itemStack.getType();
        if (material.equals(Material.WOODEN_SWORD)) return true;
        if (material.equals(Material.STONE_SWORD)) return true;
        if (material.equals(Material.IRON_SWORD)) return true;
        if (material.equals(Material.GOLDEN_SWORD)) return true;
        if (material.equals(Material.DIAMOND_SWORD)) return true;
        if (material.equals(Material.NETHERITE_SWORD)) return true;
        if (material.equals(Material.WOODEN_AXE)) return true;
        if (material.equals(Material.STONE_AXE)) return true;
        if (material.equals(Material.IRON_AXE)) return true;
        if (material.equals(Material.GOLDEN_AXE)) return true;
        if (material.equals(Material.DIAMOND_AXE)) return true;
        if (material.equals(Material.NETHERITE_AXE)) return true;
        if (material.equals(Material.WOODEN_PICKAXE)) return true;
        if (material.equals(Material.STONE_PICKAXE)) return true;
        if (material.equals(Material.IRON_PICKAXE)) return true;
        if (material.equals(Material.GOLDEN_PICKAXE)) return true;
        if (material.equals(Material.DIAMOND_PICKAXE)) return true;
        if (material.equals(Material.NETHERITE_PICKAXE)) return true;
        if (material.equals(Material.WOODEN_SHOVEL)) return true;
        if (material.equals(Material.STONE_SHOVEL)) return true;
        if (material.equals(Material.IRON_SHOVEL)) return true;
        if (material.equals(Material.GOLDEN_SHOVEL)) return true;
        if (material.equals(Material.DIAMOND_SHOVEL)) return true;
        if (material.equals(Material.NETHERITE_SHOVEL)) return true;
        if (material.equals(Material.WOODEN_HOE)) return true;
        if (material.equals(Material.STONE_HOE)) return true;
        if (material.equals(Material.IRON_HOE)) return true;
        if (material.equals(Material.GOLDEN_HOE)) return true;
        if (material.equals(Material.DIAMOND_HOE)) return true;
        if (material.equals(Material.NETHERITE_HOE)) return true;
        if (material.equals(Material.FISHING_ROD)) return true;
        if (material.equals(Material.BOW)) return true;
        if (material.equals(Material.CROSSBOW)) return true;
        if (material.equals(Material.TRIDENT)) return true;
        if (material.equals(Material.FLINT_AND_STEEL)) return true;
        if (material.equals(Material.SHEARS)) return true;
        return false;
    }

    public boolean sword(ItemStack itemStack) {
        Material material=itemStack.getType();
        if (material.equals(Material.WOODEN_SWORD)) return true;
        if (material.equals(Material.STONE_SWORD)) return true;
        if (material.equals(Material.IRON_SWORD)) return true;
        if (material.equals(Material.GOLDEN_SWORD)) return true;
        if (material.equals(Material.DIAMOND_SWORD)) return true;
        if (material.equals(Material.NETHERITE_SWORD)) return true;
        return false;
    }
}
