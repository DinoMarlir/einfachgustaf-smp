package live.einfachgustaf.mods.smp.utils.tablist

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

class TablistPlaceholderResolver(
    private val player: ServerPlayer,
    private val server: MinecraftServer
): TagResolver {

    override fun resolve(name: String, arguments: ArgumentQueue, ctx: Context): Tag? {
        return resolve(name)
    }

    override fun has(name: String): Boolean {
        return resolve(name) != null
    }

    private fun resolve(name: String): Tag? = when(name) {
        "player" -> Tag.inserting(player.name)
        "online" -> Tag.inserting(Component.text(server.playerList.players.size))
        else -> null
    }
}