package live.einfachgustaf.mods.smp.advancement

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.LOGGER
import live.einfachgustaf.mods.smp.data.db.MongoDB
import net.minecraft.advancements.*
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.item.ItemEntity
import net.silkmc.silk.core.task.mcCoroutineScope

object Advancements {
    val DEFAULT_RESOURCE = ResourceLocation("textures/gui/advancements/backgrounds/adventure.png")

    private lateinit var root: CompilableAdvancement
    private val advancements = mutableListOf<CompilableAdvancement>()

    fun advancements(): List<CompilableAdvancement> {
        return advancements
    }

    fun advancement(resourceLocation: ResourceLocation): CompilableAdvancement? {
        return advancements.firstOrNull { it.id == resourceLocation }
    }

    @Suppress("unused")
    fun advancement(path: String): CompilableAdvancement? {
        return advancements.firstOrNull { it.id == res(path) }
    }

    fun res(path: String): ResourceLocation {
        return ResourceLocation("einfachgustaf:/root/$path")
    }

    fun createTab(forAdvancement: GustafAdvancement): CompilableAdvancement {
        val compiledAdvancement = CompilableAdvancement(forAdvancement, path = "")
        advancements += compiledAdvancement
        root = compiledAdvancement
        return compiledAdvancement
    }

    fun register(forAdvancement: GustafAdvancement, path: String, parent: CompilableAdvancement? = null, x: Float = 0f, y: Float = 0f): CompilableAdvancement {
        val compiledAdvancement = CompilableAdvancement(forAdvancement, x, y, parent?.id, path)
        advancements += compiledAdvancement
        return compiledAdvancement
    }

    fun createAdvancements(serverPlayer: ServerPlayer) {
        LOGGER.debug("Creating advancements for ${serverPlayer.stringUUID}")
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                advancements.map { it.compile() },
                setOf(),
                //advancements.associate { it.id to AdvancementProgress() }
                mapOf()
            )
        )
        mcCoroutineScope.launch {
            delay(100)
            advancements.filter { it.gustafAdvancement.isUnlocked }.forEach { unlockAdvancement(serverPlayer, it) }
            awardAdvancement(serverPlayer, root, isRestore = true)
            MongoDB.getPlayerAdvancements(serverPlayer.stringUUID).forEach {
                awardAdvancement(serverPlayer, it, isRestore = true)
            }
        }
    }

    private fun unlockAdvancement(serverPlayer: ServerPlayer, advancement: CompilableAdvancement) {
        val progress = AdvancementProgress()
        val requirements = AdvancementRequirements.allOf(listOf("dummy"))
        progress.update(requirements)
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                listOf(advancement.compile()),
                setOf(),
                mapOf(advancement.id to progress)
            )
        )
    }

    suspend fun awardAdvancement(serverPlayer: ServerPlayer, advancement: CompilableAdvancement, isRestore: Boolean = false, ignoreDuplicate: Boolean = false) {
        var restoreOrDuplicate = isRestore
        val progress = AdvancementProgress()
        val requirements = AdvancementRequirements.allOf(listOf("dummy"))
        progress.update(requirements)
        progress.grantProgress("dummy")
        if (!isRestore && !ignoreDuplicate) {
            if (MongoDB.getPlayerAdvancements(serverPlayer.stringUUID).any { it.id == advancement.id }) {
                restoreOrDuplicate = true
            }
        }
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                listOf(advancement.compile(hide = false, showInChat = !restoreOrDuplicate, showToast = !restoreOrDuplicate)),
                setOf(),
                mapOf(advancement.id to progress)
            )
        )
        advancement.gustafAdvancement.unlocks.forEach {
            unlockAdvancement(serverPlayer, advancement(it)!!)
        }
        if (!restoreOrDuplicate) {
            advancement.gustafAdvancement.rewards.forEach {
                println(it)
                val itemEntity = ItemEntity(serverPlayer.level(), serverPlayer.x, serverPlayer.y, serverPlayer.z, it)
                serverPlayer.level().addFreshEntity(itemEntity)
                itemEntity.setNoPickUpDelay()
                itemEntity.setTarget(serverPlayer.uuid)

                //serverPlayer.containerMenu.broadcastChanges()
            }
        }
        MongoDB.awardAdvancement(serverPlayer.stringUUID, advancement)
    }

    fun DisplayInfo.location(x: Float, y: Float): DisplayInfo {
        setLocation(x, y)
        return this
    }
}