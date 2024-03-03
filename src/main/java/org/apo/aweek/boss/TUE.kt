package org.apo.aweek.boss

import org.apo.aweek.Aweek
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Blaze
import org.bukkit.entity.Phantom
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class TUE: Listener {
    val aweek=Aweek.Instance
    fun spawnB(p:Player) {
        p.world.spawn(p.location.add(0.0, 20.0, 0.0), Blaze::class.java) { Blaze: Blaze ->
            Blaze.maxHealth = 230.0
            Blaze.health = 9.0
            Blaze.addScoreboardTag("Tue")

            val bossBar = Bukkit.createBossBar("Ｃｏｎｆｌａｇｒａｔｉｏｎ (Tuesday)", BarColor.YELLOW, BarStyle.SEGMENTED_20)
            bossBar.isVisible = true
            bossBar.progress = Blaze.health / Blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
            for (player in p.world.players) {
                if (player.location.distance(Blaze.location) <= 70) {
                    bossBar.addPlayer(player)
                }
            }
            object : BukkitRunnable() {
                override fun run() {
                    if (!Blaze.isDead) {
                        bossBar.progress = Blaze.health / Blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
                        for (entity in Blaze.getNearbyEntities(100.0, 100.0, 100.0)) {
                            if (entity is Player) {
                                Blaze.target = entity
                            }
                        }
                    } else {
                        bossBar.removeAll()
                    }
                }
            }.runTaskTimer(aweek, 0L, 1L)
        }
    }

    @EventHandler
    fun Death(e: EntityDeathEvent) {
        if (e.entity.scoreboardTags.contains("tue")) {
            e.droppedExp = 230
            e.drops.clear()
            val i = ItemStack(Material.BLAZE_ROD)
            val itemMeta = i.itemMeta
            itemMeta.setCustomModelData(1)
            itemMeta.setDisplayName("${ChatColor.LIGHT_PURPLE}잉거불창")
            i.setItemMeta(itemMeta)
            e.drops.add(i)
        }
    }



    @EventHandler
    fun Damage(e: EntityDamageByEntityEvent) {
        if (e.entity is Phantom) {
            if (e.entity.scoreboardTags.contains("Tue")) {
                Bukkit.broadcastMessage("${e.damage} ${e.cause}")
                if (e.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    e.isCancelled = true
                }
            }
        }
        e.entity.sendMessage(e.cause.toString())
    }
}