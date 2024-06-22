package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.LOGGER
import net.minecraft.advancements.*
import net.minecraft.advancements.critereon.ImpossibleTrigger
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import java.util.*

object Advancements {
    val DEFAULT_RESOURCE = ResourceLocation("textures/gui/advancements/backgrounds/adventure.png")

    private val advancements = mutableListOf<AdvancementHolder>()

    fun advancement(path: String): AdvancementHolder? {
        return advancements.firstOrNull { it.id == res(path) }
    }

    private fun res(path: String): ResourceLocation {
        return ResourceLocation("einfachgustaf:/root/$path")
    }

    fun register(forAdvancement: GustafAdvancement, path: String, parent: AdvancementHolder? = null): AdvancementHolder {
        val entry = Advancement.Builder.advancement()
            .display(DisplayInfo(
                forAdvancement.displayIcon,
                forAdvancement.title,
                forAdvancement.description,
                Optional.of(forAdvancement.backgroundResource),
                forAdvancement.type,
                true,
                true,
                true
            ))
            .addCriterion("dummy", CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance()))
        if (parent != null) {
            entry.parent(parent)
        }
        val advancementHolder = entry.build(res(path))
        advancements += advancementHolder
        return advancementHolder
    }

    fun createAdvancements(serverPlayer: ServerPlayer) {
        LOGGER.info("create")
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                advancements,
                setOf(),
                //advancements.associate { it.id to AdvancementProgress() }
                mapOf()
            )
        )

    }

    fun awardAdvancement(serverPlayer: ServerPlayer, advancement: AdvancementHolder) {
        val progress = AdvancementProgress()
        val requirements = AdvancementRequirements.allOf(listOf("dummy"))
        progress.update(requirements)
        progress.grantProgress("dummy")
        serverPlayer.connection.send(
            ClientboundUpdateAdvancementsPacket(
                false,
                listOf(),
                setOf(),
                mapOf(advancement.id to progress)
            )
        )
    }
}