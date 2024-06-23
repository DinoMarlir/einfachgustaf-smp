package live.einfachgustaf.mods.smp.advancement

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.LOGGER
import net.minecraft.advancements.*
import net.minecraft.advancements.critereon.ImpossibleTrigger
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.silkmc.silk.core.task.mcCoroutineScope
import java.util.*

object Advancements {
    val DEFAULT_RESOURCE = ResourceLocation("textures/gui/advancements/backgrounds/adventure.png")

    private lateinit var root: CompiledAdvancement
    private val advancements = mutableListOf<CompiledAdvancement>()

    fun advancements(): List<CompiledAdvancement> {
        return advancements
    }

    fun advancement(resourceLocation: ResourceLocation): CompiledAdvancement? {
        return advancements.firstOrNull { it.id == resourceLocation }
    }

    fun advancement(path: String): CompiledAdvancement? {
        return advancements.firstOrNull { it.id == res(path) }
    }

    private fun res(path: String): ResourceLocation {
        return ResourceLocation("einfachgustaf:/root/$path")
    }

    fun createTab(forAdvancement: GustafAdvancement): CompiledAdvancement {
        val entry = Advancement.Builder.advancement()
            .display(DisplayInfo(
                forAdvancement.displayIcon,
                forAdvancement.title,
                forAdvancement.description,
                Optional.of(forAdvancement.backgroundResource),
                forAdvancement.type,
                false,
                true,
                false
            ))
            .addCriterion("dummy", CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance()))
        val advancementHolder = entry.build(res(""))
        val compiledAdvancement = CompiledAdvancement(forAdvancement, advancementHolder)
        advancements += compiledAdvancement
        root = compiledAdvancement
        return compiledAdvancement
    }

    fun register(forAdvancement: GustafAdvancement, path: String, parent: CompiledAdvancement? = null, x: Float = 0f, y: Float = 0f): CompiledAdvancement {
        val entry = Advancement.Builder.advancement()
            .display(DisplayInfo(
                forAdvancement.displayIcon,
                forAdvancement.title,
                forAdvancement.description,
                Optional.of(forAdvancement.backgroundResource),
                forAdvancement.type,
                true,
                true,
                false
            ).location(x, y))
            .addCriterion("dummy", CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance()))
        if (parent != null) {
            entry.parent(parent.holder)
        }
        val advancementHolder = entry.build(res(path))
        val compiledAdvancement = CompiledAdvancement(forAdvancement, advancementHolder)
        advancements += compiledAdvancement
        return compiledAdvancement
    }

    fun createAdvancements(serverPlayer: ServerPlayer) {
        LOGGER.debug("Creating advancements for ${serverPlayer.stringUUID}")
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                advancements.map { it.holder },
                setOf(),
                //advancements.associate { it.id to AdvancementProgress() }
                mapOf()
            )
        )
        mcCoroutineScope.launch {
            delay(100)
            advancements.forEach { unlockAdvancement(serverPlayer, it) }
            awardAdvancement(serverPlayer, root)
        }
    }

    private fun unlockAdvancement(serverPlayer: ServerPlayer, advancement: CompiledAdvancement) {
        val progress = AdvancementProgress()
        val requirements = AdvancementRequirements.allOf(listOf("dummy"))
        progress.update(requirements)
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                listOf(advancement.holder),
                setOf(),
                mapOf(advancement.id to progress)
            )
        )
    }

    fun awardAdvancement(serverPlayer: ServerPlayer, advancement: CompiledAdvancement) {
        val progress = AdvancementProgress()
        val requirements = AdvancementRequirements.allOf(listOf("dummy"))
        progress.update(requirements)
        progress.grantProgress("dummy")
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                listOf(advancement.holder),
                setOf(),
                mapOf(advancement.id to progress)
            )
        )
    }

    fun DisplayInfo.location(x: Float, y: Float): DisplayInfo {
        setLocation(x, y)
        return this
    }
}