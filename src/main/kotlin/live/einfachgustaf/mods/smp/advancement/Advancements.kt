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

    /**
     * @return all registered advancements in a pre-compiled state
     */
    fun advancements(): List<CompilableAdvancement> {
        return advancements
    }

    /**
     * @param resourceLocation resource identifier to get advancement from
     * @return advancement with the given identifier
     */
    fun advancement(resourceLocation: ResourceLocation): CompilableAdvancement? {
        return advancements.firstOrNull { it.id == resourceLocation }
    }

    /**
     * @param path path of resource identifier to get advancement from
     * @return advancement with the given identifier path
     */
    @Suppress("unused")
    fun advancement(path: String): CompilableAdvancement? {
        return advancements.firstOrNull { it.id == res(path) }
    }

    /**
     * @param path path to create resource identifier from
     * @return resource identifier from the given path
     */
    fun res(path: String): ResourceLocation {
        return ResourceLocation("einfachgustaf:/root/$path")
    }

    /**
     * @param gustafAdvancement the root advancement for the tab
     * @return the registered root-advancement in a pre-compiled state
     */
    fun createTab(gustafAdvancement: GustafAdvancement): CompilableAdvancement {
        val compiledAdvancement = CompilableAdvancement(gustafAdvancement, path = "")
        advancements += compiledAdvancement
        root = compiledAdvancement
        return compiledAdvancement
    }

    /**
     * @param gustafAdvancement the advancement to create and register
     * @param path the path of the resource identifier
     * @param parent the parent of the advancement
     * @param x x coordinate in the advancement screen
     * @param y y coordinate in the advancement screen
     * @return the registered advancement in a pre-compiled state
     */
    fun register(gustafAdvancement: GustafAdvancement, path: String, parent: CompilableAdvancement? = null, x: Float = 0f, y: Float = 0f): CompilableAdvancement {
        val compiledAdvancement = CompilableAdvancement(gustafAdvancement, x, y, parent?.id, path)
        advancements += compiledAdvancement
        return compiledAdvancement
    }

    /**
     * @param serverPlayer player to create advancements for
     */
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

    /**
     * @param x x location
     * @param y y location
     */
    fun DisplayInfo.location(x: Float, y: Float): DisplayInfo {
        setLocation(x, y)
        return this
    }
}