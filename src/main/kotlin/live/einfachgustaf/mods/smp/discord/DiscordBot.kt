package live.einfachgustaf.mods.smp.discord

import dev.minn.jda.ktx.jdabuilder.intents
import dev.minn.jda.ktx.jdabuilder.light
import net.dv8tion.jda.api.requests.GatewayIntent

/**
 * Initializes the Discord bot if the token is provided.
 */
object DiscordBot {
    private val botToken = System.getenv("DISCORD_BOT_TOKEN")

    init {
        if (botToken == null) {
            println("No Discord bot token found, not starting Discord bot")
        } else {
            initBot()
        }
    }

    private fun initBot() {
        val jda = light(botToken, enableCoroutines = true) {
            intents += listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
        }

        ChatSync.init(jda)
    }
}