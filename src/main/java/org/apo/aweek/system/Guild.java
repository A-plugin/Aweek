package org.apo.aweek.system;

import org.apo.aweek.Aweek;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Guild {
    Aweek aweek = Aweek.Instance;
    public File guildF = new File(aweek.getDataFolder(), "Guild.yml");
    public FileConfiguration config = YamlConfiguration.loadConfiguration(guildF);
    List<String> guildL=new ArrayList<>();

    public void guildLoading() {
        guildL = config.getStringList("guild");
    }



    public void create(String Gn, String owner, Player p) {
        if (aweek.economy !=null){
            if (aweek.economy.getBalance(p)>=5000){
                Gc(Gn, owner, p);
            } else {
                p.sendMessage(ChatColor.RED+"길드 창설에는 5000$ 이상의 돈이 필요합니다.");
            }
        } else {
            Gc(Gn, owner, p);
        }
    }

    private void Gc(String Gn, String owner, Player p) {
        String guildOwner = config.getString(Gn + ".own");
        if (guildOwner != null && guildOwner.equals(owner)) {
            p.sendMessage("§c이미 주인이 존재하는 길드입니다.");
        }

        if (config.get(p.getName()) == null) {
            config.set(Gn + ".own", owner);
            p.sendMessage("§a길드가 만들어졌습니다. \n 길드 이름: " + Gn + " \n 길드 주인: " + owner);
            if (!guildL.contains(Gn)) {
                guildL.add(Gn);
                config.set("guild", guildL);
            }
            if (aweek.economy!=null) {
                if (aweek.economy.getBalance(p)>=5000){
                    aweek.economy.withdrawPlayer(p, 5000.0);
                }
            }
            try {
                config.save(guildF);
            } catch (IOException er) {
                er.printStackTrace();
            }
        } else {
            p.sendMessage(ChatColor.RED + "이미 길드가 존재합니다.");
        }
    }

    public void join(String Gn, String nick, Player p) {
        if (config.get(Gn+".own")!=null){
            List<String> mb = config.getStringList(Gn + ".mb");
            if (mb == null) {
                mb = new ArrayList<>();
            }

            if (!nick.equalsIgnoreCase(config.getString(Gn + ".own"))) {
                if (!mb.contains(nick)) {
                    mb.add(nick);
                    config.set(Gn + ".mb", mb);
                    saveConfig(guildF, config);
                    p.sendMessage("§a" + nick + "이(가) " + Gn + "길드에 소속됩니다.");
                } else {
                    p.sendMessage("§c이미 길드에 소속된 플레이어입니다.");
                }
            } else {
                p.sendMessage("§c"+nick+"은(는) 한 길드의 길드장입니다.");
            }
        } else {
            p.sendMessage("§c존재하지 않는 길드입니다.");
        }
    }

    private void saveConfig(File guildF, FileConfiguration config) {
        try {
            config.save(guildF);
        } catch (IOException er) {
            er.printStackTrace();
        }
    }

    public void leave(String Gn, String nick, Player p) {
        if (config.get(Gn+"mb") != null) {
            List<String> mb= new ArrayList<>();
            if (!config.get(Gn+".own").equals(nick)){
                if (config.get(Gn + "mb") != null) {
                    for (String mem : config.getStringList(Gn + ".mb")) {
                        if (!mem.equalsIgnoreCase(nick)) {
                            mb.add(mem);
                        }
                    }
                }
                config.set(Gn + ".mb", mb);
                saveConfig(guildF, config);
                p.sendMessage("§a"+Gn+"길드를 나갔습니다.");
            } else {
                p.sendMessage("§c길드장은 길드를 나갈 수 없습니다!");
            }
        } else {
            p.sendMessage("§c"+nick+"은(는) 길드에 소속되어있지 않습니다.");
        }
    }

    public void remove(String Gn, String nick, Player p) {}

    public void list(String Gn, String nick, Player p) {}

}
