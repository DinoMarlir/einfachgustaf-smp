package live.einfachgustaf.mods.smp.features

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import live.einfachgustaf.mods.smp.Entrypoint.logger
import live.einfachgustaf.mods.smp.features.StatusRegistry.playerStatusMap
import live.einfachgustaf.mods.smp.features.StatusRegistry.readFromFile
import live.einfachgustaf.mods.smp.features.StatusRegistry.statusList
import live.einfachgustaf.mods.smp.utils.dataFolder
import live.einfachgustaf.mods.smp.utils.json
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.literalText
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.io.File

object StatusRegistry {
    val statusFile = File(dataFolder, "status.json")
    val statusList = arrayListOf<Status>()
    val playerStatusMap = hashMapOf<ServerPlayer, Status>()

    fun add(status: Status) {
        readFromFile()
        statusList.add(status)
        statusFile.writeText(json.encodeToString<List<Status>>(statusList))
    }

    fun remove(status: Status) {
        readFromFile()
        statusList.remove(status)
        statusFile.writeText(json.encodeToString<List<Status>>(statusList))
    }

    fun readFromFile(): List<Status> {
        if (!statusFile.exists()) {
            statusFile.writeText(json.encodeToString<List<Status>>(listOf(
                Status("ONLINE", 0x00FF00)
            )))
            return listOf()
        }

        kotlin.runCatching {
            json.decodeFromString<List<Status>>(statusFile.readText()).forEach {
                if (!statusList.contains(it)) {
                    statusList.add(it)
                }
            }
        }

        return statusList
    }
}

val statusCommand = command("status") {
    runs {
        source.sendSystemMessage(literalText("Available: ${statusList.joinToString(", ") { it.name }}") {  }) //TODO: make it prettier
    }

    literal("add") {
        requiresPermissionLevel(2) //TODO: Optional LuckPerms Implementation
        argument<String>("status") { statusArgument ->
            suggestList {
                statusList.filter { !it.name.contains(" ") }.map { it.name }
            }
        }
    }

    literal("remove") {
        requiresPermissionLevel(2) //TODO: Optional LuckPerms Implementation
        argument<String>("status") { statusArgument ->
            suggestList {
                statusList.filter { !it.name.contains(" ") }.map { it.name }
            }
        }
    }

    argument<String>("status") { statusArgument ->
        suggestList {
            statusList.filter { !it.name.contains(" ") }.map { it.name }
        }

        runs {
            if (!statusList.map { it.name }.contains(statusArgument())) return@runs
            playerStatusMap[source.playerOrException] = statusList.first { it.name == statusArgument() }
            source.sendSystemMessage(Component.literal("Your status is now '${statusArgument()}'.")) // TODO: translateable
        }
    }
}

object StatusFeature {

    fun injectTablist(
        serverPlayer: ServerPlayer,
        cir: CallbackInfoReturnable<Component>
    ) {
        logger.info("LOL CALLED")
        val status = playerStatusMap[serverPlayer] ?: return
        cir.returnValue = literalText {
            text("[")
            text(status.name) { color = status.color }
            text("] ")
            text(serverPlayer.name)
        }
    }

    init {
        readFromFile()
        statusCommand
    }
}

@Serializable
data class Status(
    val name: String,
    val color: Int
)