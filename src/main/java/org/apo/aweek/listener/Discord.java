package org.apo.aweek.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ActivityFlag;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.apo.aweek.Aweek;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.EnumSet;

public class Discord extends ListenerAdapter {
    Aweek aweek= Aweek.Instance;
    RichPresence richPresence=new RichPresence() { //Client secret : 1nyttzKTyjdEc7AvRI2IcJ9Hx8incCcO
        @Override
        public long getApplicationIdLong() {
            return 1200731517077426257L;
        }

        @NotNull
        @Override
        public String getApplicationId() {
            return null;
        }

        @Nullable
        @Override
        public String getSessionId() {
            return null;
        }

        @Nullable
        @Override
        public String getSyncId() {
            return null;
        }

        @Override
        public int getFlags() {
            return ApplicationInfo.Flag.EMBEDDED.ordinal();
        }

        @Override
        public EnumSet<ActivityFlag> getFlagSet() {
            return EnumSet.of(ActivityFlag.INSTANCE);
        }

        @Nullable
        @Override
        public String getDetails() {
            return "A-Week Minecraft Server";
        }

        @Nullable
        @Override
        public Party getParty() {
            long e=Bukkit.getOfflinePlayers().length;
            Party party=new Party("ae488379-351d-4a4f-ad32-2b9b01c91657",e,20L);
            return party;
        }

        @Nullable
        @Override
        public Image getLargeImage() {
            Image image=new Image(1200731517077426257L,"kakaotalk_20240127_191324440","A-week");
            return image;
        }

        @Nullable
        @Override
        public Image getSmallImage() {
            Image image=new Image(1200731517077426257L,"ae488379-351d-4a4f-ad32-2b9b01c91657","A-week");
            return image;
        }

        @Override
        public boolean isRich() {
            return false;
        }

        @Nullable
        @Override
        public RichPresence asRichPresence() {
            return null;
        }

        @NotNull
        @Override
        public String getName() {
            return "Aweek 마인크래프트 서버";
        }

        @Nullable
        @Override
        public String getState() {
            return "일주일의 시작과 끝";
        }

        @Nullable
        @Override
        public String getUrl() {
            return "https://discord.gg/pFMUsktFn";
        }

        @NotNull
        @Override
        public ActivityType getType() {
            ActivityType activityTyp=ActivityType.PLAYING;
            return activityTyp;
        }

        @Nullable
        @Override
        public Timestamps getTimestamps() {
            Timestamps timestamps=new Timestamps(0L, 1507665886L);
            return timestamps;
        }

        @Nullable
        @Override
        public EmojiUnion getEmoji() {
            return null;
        }

        @NotNull
        @Override
        public Activity withState(@Nullable String s) {
            Activity activity=Activity.playing("A-WEEK");
            return activity;
        }
    };

    @Override
    public void onReady(ReadyEvent e) {
        aweek.dj().getPresence().setPresence(richPresence,false);
        aweek.dj().getPresence().setStatus(OnlineStatus.ONLINE);
        aweek.dj().getPresence().setIdle(richPresence.isRich());

    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw();

        if (content.equals("!verify") || content.equals("!인증")) {
            Button button = Button.success("verify", "verify").withEmoji(Emoji.fromFormatted("✅"));
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("A-week 서버 인증하기")
                    .setColor(Color.ORANGE);

            event.getChannel().deleteMessageById(event.getMessage().getId());
            event.getMessage().replyEmbeds(embed.build())
                    .addActionRow(button)
                    .complete();

            return;
        }
        Bukkit.broadcastMessage("§9[DISCORD]§f " + event.getMember().getNickname() + "§a >>§f " + content);
    }
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {
        if (e.getButton().getId().equalsIgnoreCase("verify")) {
            TextInput Nick = TextInput.create("nick", "NickName", TextInputStyle.SHORT)
                    .setPlaceholder("인증 번호")
                    .setMinLength(3)
                    .setMaxLength(100)
                    .build();
            Modal modal=Modal.create("pnn","인증하기")
                    .addComponents(ActionRow.of(Nick))
                    .build();
            e.replyModal(modal).queue();
        }
    }

    public String nick,dis;
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {
        if (e.getModalId().equalsIgnoreCase("pnn")) {
            nick=e.getValue("nick").getAsString();
            dis=e.getMember().getId();
            Bukkit.broadcastMessage(nick+": "+ dis);
            EmbedBuilder embedBuilder=new EmbedBuilder()
                    .setTitle("인증 성공!")
                    .setColor(e.getMember().getColor())
                    .setDescription(Bukkit.getIp())
                    .setThumbnail("https://cravatar.eu/helmavatar/"+nick+"/64")
                    .setAuthor(e.getMember().getUser().getName(),e.getMember().getAvatarUrl(),e.getMember().getDefaultAvatarUrl())
                    .setImage(e.getMember().getAvatarUrl())
                    .setFooter("인증됨", "https://png.pngtree.com/png-vector/20191113/ourmid/pngtree-green-check-mark-icon-flat-style-png-image_1986021.jpg")
                    ;
            e.replyEmbeds(embedBuilder.build()).queue();

        }
    }
}
