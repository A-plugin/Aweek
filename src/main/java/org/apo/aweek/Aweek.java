package org.apo.aweek;

import net.milkbowl.vault.economy.Economy;
import org.apo.aweek.Commands.JokeC;
import org.apo.aweek.Commands.aweekC;
import org.apo.aweek.Commands.aweekTab;
import org.apo.aweek.boss.Mon;
import org.apo.aweek.gui.Artifact;
import org.apo.aweek.gui.Job;
import org.apo.aweek.gui.Mainmenu;
import org.apo.aweek.gui.info;
import org.apo.aweek.listener.listener;
import org.apo.aweek.listener.scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Aweek extends JavaPlugin implements Listener {
    public static Aweek Instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        if (Aweek.Instance==null) {
            Aweek.Instance=this;
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && setupEconomy()) {
                // PlaceholderAPI가 로드되었을 때 이벤트 리스너를 등록합니다.
                getServer().getPluginManager().registerEvents(new Mainmenu(), this);
                getServer().getPluginManager().registerEvents(new info(), this);
                getServer().getPluginManager().registerEvents(new listener(), this);
                getServer().getPluginManager().registerEvents(new Job(), this);
                getServer().getPluginManager().registerEvents(new scoreboard(), this);
                getServer().getPluginManager().registerEvents(this, this);
                getServer().getPluginManager().registerEvents(new Artifact(), this);

                getServer().getPluginManager().registerEvents(new Mon(), this);

                getCommand("joke").setExecutor(new JokeC());
                getCommand("aweek").setExecutor(new aweekC());
                getCommand("aweek").setTabCompleter(new aweekTab());

                getLogger().info("Aweek Server plugin");
                getLogger().info("");
            } else {
                // PlaceholderAPI가 없을 경우 콘솔에 메시지를 출력합니다.
                getLogger().warning("PlaceholderAPI 또는 Vault 플러그인을  찾을 수 없습니다. 플러그인을 비활성화합니다.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } else {
            throw new IllegalStateException("Plugin already initialized!");
        }
    }

    public Economy economy=null;
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false; // Vault 플러그인이 없음
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false; // 경제 서비스 등록 실패
        }

        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public void onDisable() {
        getLogger().info("플러그인이 비활설화됬습니다.");
    }
}
