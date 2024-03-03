package org.apo.aweek.system

import org.apo.aweek.Aweek
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLevelChangeEvent


class Level: Listener {
    val aweek=Aweek.Instance
    fun Lv(lv:Int,p:Player) {
        aweek.config.set("${p.name}.Lv", lv)
    }
    @EventHandler
    fun LvChang(e:PlayerLevelChangeEvent) {
        if (e.oldLevel < e.newLevel) {
            Lv(e.newLevel, e.player)
        }
    }
}