package com.atherys.skills.facade;

import com.google.inject.Singleton;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;

@Singleton
public class SkillMessagingFacade {

    public static final Text PREFIX = Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "Skills", TextColors.GOLD, "] ", TextColors.RESET);

    public void info(MessageReceiver receiver, Object... msg) {
        receiver.sendMessage(Text.of(PREFIX, Text.of(msg)));
    }
}
