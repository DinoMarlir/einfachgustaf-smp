package live.einfachgustaf.mods.smp.discord

import dev.minn.jda.ktx.events.listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.silkmc.silk.core.Silk
import net.silkmc.silk.core.text.broadcastText
import net.silkmc.silk.core.text.literalText

object ChatSync {
    private val syncChannel = System.getenv("DISCORD_CHAT_SYNC_CHANNEL").toLongOrNull()

    fun init(jda: JDA) {
        if (syncChannel == null) {
            println("No Discord chat sync channel found, not connecting to Discord chat. Please set the DISCORD_CHAT_SYNC_CHANNEL environment variable to the channel ID")
            return
        }

        ServerMessageEvents.CHAT_MESSAGE.register { _, _, _ ->
            // TODO: send chat message to discord
        }

        jda.listener<MessageReceivedEvent> {
            val message = it.message
            val member = it.member?.effectiveName

            if (it.channel.idLong != syncChannel) return@listener

            // TODO: use custom glyphs for formatting
            Silk.server?.broadcastText(literalText {
                text("[Discord] ")
                text(member ?: "Unknown")
                text(": ")
                text(message.contentDisplay)
            })
        }
    }
}