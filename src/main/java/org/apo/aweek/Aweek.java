package org.apo.aweek;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.milkbowl.vault.economy.Economy;
import org.apo.aweek.Commands.*;
import org.apo.aweek.boss.Mon;
import org.apo.aweek.gui.Job;
import org.apo.aweek.gui.Mainmenu;
import org.apo.aweek.gui.info;
import org.apo.aweek.listener.Discord;
import org.apo.aweek.listener.ListenerK;
import org.apo.aweek.listener.listener;
import org.apo.aweek.listener.scoreboard;
import org.apo.aweek.system.Artifact;
import org.apo.aweek.system.Guild;
import org.apo.aweek.system.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class Aweek extends JavaPlugin implements Listener {
    public static Aweek Instance;
    public static JDA jda;
    public JDA dj() {
        return jda;
    }

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
                getServer().getPluginManager().registerEvents(new ListenerK(), this);
                getServer().getPluginManager().registerEvents(new Level(), this);

                getServer().getPluginManager().registerEvents(new Mon(), this);

                getCommand("joke").setExecutor(new JokeC());
                getCommand("aweek").setExecutor(new aweekC());
                getCommand("aweek").setTabCompleter(new aweekTab());
                getCommand("guild").setExecutor(new GuildC());
                getCommand("guild").setTabCompleter(new GuildTab());

                getLogger().info("Aweek Server");
                jda = JDABuilder.createDefault("MTIwMDczMTUxNzA3NzQyNjI1Nw.G8ALVF.98vb6CBozuPpY2yhqf4dzz0CI0QneaqM0Ytx0I", GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                        .addEventListeners(new Discord())
                        .setActivity(Activity.playing("A-week"))
                        .setStatus(OnlineStatus.ONLINE)
                        .build();


                guild();
            } else {
                // PlaceholderAPI가 없을 경우 콘솔에 메시지를 출력합니다.
                getLogger().warning("PlaceholderAPI 또는 Vault 플러그인을  찾을 수 없습니다. 플러그인을 비활성화합니다.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } else {
            throw new IllegalStateException("Plugin already initialized!");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        // 헬멧 아바타 다운로드 및 저장
        downloadAndSaveAvatar(playerName);
    }

    private void downloadAndSaveAvatar(String playerName) {
        String baseURL = "https://cravatar.eu/helmavatar/";
        String size = "64";
        String urlString = baseURL + playerName + "/" + size;

        try {
            URL url = new URL(urlString);
            InputStream inputStream = url.openStream();

            Path folderPath = getDataFolder().toPath().resolve("PlayerImg");
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            Path filePath = folderPath.resolve(playerName + ".png");
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
            getLogger().warning("아바타 이미지 다운로드 중 오류가 발생했습니다.");
        }
    }

    public void guild() {
        Guild guild=new Guild();
        guild.guildLoading();
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
