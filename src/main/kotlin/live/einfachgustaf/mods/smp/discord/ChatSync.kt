package live.einfachgustaf.mods.smp.discord

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.generics.getChannel
import dev.minn.jda.ktx.messages.MessageCreate
import live.einfachgustaf.mods.smp.LOGGER
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.silkmc.silk.core.Silk
import net.silkmc.silk.core.text.broadcastText
import net.silkmc.silk.core.text.literalText

/**
 * Handles the synchronization of chat messages between Minecraft and Discord.
 */
object ChatSync {
    private val syncChannel = System.getenv("DISCORD_CHAT_SYNC_CHANNEL").toLongOrNull()

    fun init(jda: JDA) {
        if (syncChannel == null) {
            println("No Discord chat sync channel found, not connecting to Discord chat. Please set the DISCORD_CHAT_SYNC_CHANNEL environment variable to the channel ID")
            return
        }

        // TODO: fix channel not found
        minecraftToDiscord(
            jda.getPrivateChannelById(syncChannel) ?: jda.getChannel(syncChannel) ?: return LOGGER.error(
                "Failed to find Discord Channel for Minecraft chat sync!"
            )
        )
        discordToMinecraft(jda)
    }

    private fun discordToMinecraft(jda: JDA) = jda.listener<MessageReceivedEvent> {
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

    private fun minecraftToDiscord(channel: MessageChannel) =
        ServerMessageEvents.CHAT_MESSAGE.register { message, player, _ ->
            kotlin.runCatching {
                channel.sendMessage(
                    MessageCreate {
                        content = "[Minecraft] ${player.name.tryCollapseToString()}: ${message.signedContent()}"
                    }
                ).queue()
            }.onFailure {
                LOGGER.error("Failed to send message to Discord", it)
            }
        }
}