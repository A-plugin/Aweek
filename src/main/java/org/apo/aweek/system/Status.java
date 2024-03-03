package org.apo.aweek.system;

import org.apo.aweek.Aweek;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.awt.*;

public class Status {
    Aweek aweek=Aweek.Instance;

    public String Nick,Uuid, Job, Money, Hp, Lv;
    public void Player(Player p) {
        Nick=p.getName();
        Uuid= String.valueOf(p.getUniqueId());
        Job=aweek.getConfig().getString(p.getName()+".job");
        Money=aweek.getConfig().getString(p.getName()+".money");
        Hp=p.getHealth()+"/"+aweek.getConfig().getString(p.getName()+".MaxHp");
        Lv=aweek.getConfig().getString(p.getName()+".Lv");
    }
    public void test() {
        JFrame j = new JFrame();
        j.setTitle("상태창");
        j.setLayout(new BorderLayout());

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel N = new JLabel(Nick);
        JLabel U = new JLabel("UUID: "+Uuid);
        JLabel J = new JLabel("Job: "+Job);
        JLabel M = new JLabel("Money: "+Money);
        JLabel H = new JLabel("HP: " + Hp);
        JLabel L=new JLabel("Lv: "+Lv);
        JLabel I = new JLabel();

        ImageIcon img = new ImageIcon(String.valueOf(aweek.getDataFolder().toPath().resolve("PlayerImg/" + Nick + ".png")));
        I.setIcon(img);

        JPanel jPanel1 = new JPanel(new FlowLayout());
        jPanel1.add(N);
        jPanel1.add(I);

        jPanel.add(jPanel1);
        jPanel.add(U);
        jPanel.add(H);
        jPanel.add(L);
        jPanel.add(J);
        jPanel.add(M);


        j.add(jPanel, BorderLayout.CENTER);

        j.setSize(400, 500);
        j.setVisible(true);
    }
}