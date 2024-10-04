package live.einfachgustaf.mods.smp.utils.tablist

import kotlinx.serialization.Serializable
import live.einfachgustaf.mods.smp.internal.Reloadable
import live.einfachgustaf.mods.smp.utils.AbstractCachedConfig
import live.einfachgustaf.mods.smp.utils.miniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import kotlin.io.path.Path

@Serializable
data class TablistConfig(
    val updateTime: Int,
    val header: Set<String>,
    val footer: Set<String>
) {
    companion object {
        fun default() = TablistConfig(
            updateTime = 20,
            header = setOf(
                "<gradient:#00a5ff:#0077b7>ᴇɪɴғᴀᴄʜɢᴜsᴛᴀғ.ʟɪᴠᴇ</gradient> <gray>-</gray> <gradient:#f4fc03:#fcc203>ᴍɪɴᴇᴄʀᴀғᴛ ᴏᴄᴇᴀɴ</gradient>",
                "<gradient:#fcc203:#fcc203>ᴀᴋᴛᴜᴇʟʟᴇʀ sᴇʀᴠᴇʀ</gradient> <grey>➥</grey> <gradient:#fc0b03:#fc3503>sᴍᴘ</gradient>",
                "<gradient:#fcc203:#fcc203>Oɴʟɪɴᴇ Pʟᴀʏᴇʀs</gradient> <grey>➥</grey> <gradient:#fc0b03:#fc3503><players.online>/<players.max></gradient>",
                ""
            ),
            footer = setOf(
                "",
                "<gradient:#00a5ff:#0077b7>ᴘᴏᴡᴇʀᴇᴅ ʙʏ </gradient><gradient:#dbd8d0:#ffffff>FᴀʙʀɪᴄMC</gradient>",
                "<gradient:#00a5ff:#0077b7>ᴅᴇᴠᴇʟᴏᴘᴇᴅ ʙʏ </gradient><gradient:#00ffdd:#08c9b0>ᴛʜᴇ ᴇɪɴғᴀᴄʜɢᴜsᴛᴀғ.ʟɪᴠᴇ ᴅᴇᴠ ᴛᴇᴀᴍ</gradient> <gradient:#00a5ff:#0077b7>ᴡɪᴛʜ</gradient> <red>❤</red>"
            )
        )

        val config = TablistCachedConfig()
    }

    class TablistCachedConfig: AbstractCachedConfig<TablistConfig>(
        path = Path("config/einfachgustaf-smp/tablist.json"),
        default = default(),
        serializer = serializer(),
        deserializer = serializer()
    ), Reloadable {
        override fun reload() {
            get(cached = false)
        }
    }

    fun buildHeader(tagResolver: TagResolver): Component = deserialize(header, tagResolver)

    fun buildFooter(tagResolver: TagResolver): Component = deserialize(footer, tagResolver)

    private fun deserialize(input: Set<String>, tagResolver: TagResolver): Component = miniMessage.deserialize(
        input.joinToString("<newline>"),
        tagResolver
    )
}

// <gradient:#00a5ff:#0077b7>ᴇɪɴғᴀᴄʜɢᴜsᴛᴀғ.ʟɪᴠᴇ</gradient> <gray>-</gray> <gradient:#f4fc03:#fcc203>ᴍɪɴᴇᴄʀᴀғᴛ ᴏᴄᴇᴀɴ</gradient><newLine><newLine><gradient:#fcc203:#fcc203>ᴀᴋᴛᴜᴇʟʟᴇʀ sᴇʀᴠᴇʀ</gradient> <grey>➥</grey> <gradient:#fc0b03:#fc3503>sᴍᴘ</gradient><newline><gradient:#fcc203:#fcc203>ᴀᴋᴛᴜᴇʟʟᴇʀ ᴘʀᴏxʏ</gradient> <grey>➥</grey> <gradient:#fc0b03:#fc3503>ᴅᴇ-ғʀᴀ</gradient><newline>
// <newline><gradient:#00a5ff:#0077b7>ᴘᴏᴡᴇʀᴇᴅ ʙʏ </gradient><gradient:#dbd8d0:#ffffff>ɴᴇxᴛᴄʟᴜsᴛᴇʀ</gradient><newline><gradient:#00a5ff:#0077b7>ᴅᴇᴠᴇʟᴏᴘᴇᴅ ʙʏ </gradient><gradient:#00ffdd:#08c9b0>ᴛʜᴇ ᴇɪɴғᴀᴄʜɢᴜsᴛᴀғ.ʟɪᴠᴇ ᴅᴇᴠ ᴛᴇᴀᴍ</gradient> <gradient:#00a5ff:#0077b7>ᴡɪᴛʜ</gradient> <red>❤</red>