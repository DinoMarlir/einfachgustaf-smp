package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.Job
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.CompilableAdvancement
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerCollectItemEvent
import live.einfachgustaf.mods.smp.event.PlayerCraftItemEvent
import live.einfachgustaf.mods.smp.event.PlayerDropItemEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Items
import net.silkmc.silk.core.task.mcCoroutineTask
import net.silkmc.silk.core.text.literalText
import kotlin.time.Duration.Companion.minutes

// TODO: don't run job if player has the advancement already

object WhatTimeIsIt {

    val playerJobMap = mutableMapOf<ServerPlayer, Job>()

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.CLOCK.defaultInstance,
                literalText("What time is it?"),
                literalText("Crafte dir eine Uhr und behalte diese für 20 min in deinem Inventar."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    Items.COMPASS.defaultInstance,
                )
            ),
            "whattimeisit",
            beginnerRoot,
            x = 2 * 1.5f,
            y = 1.5f
        )

        collectionEvents(advancement)
        removeEvents()
    }

    private fun collectionEvents(advancement: CompilableAdvancement) {
        ServerPlayConnectionEvents.JOIN.register { event, _, _ ->
            val player = event.player

            if (player.inventory.items.any { it.item == Items.CLOCK }) {
                startDelayedJob(player, advancement)
            }
        }

        subscribeToEvent<PlayerCraftItemEvent> {
            if (it.item.item == Items.CLOCK) {
                startDelayedJob(it.player.asServerPlayer() ?: return@subscribeToEvent, advancement)
            }
        }

        subscribeToEvent<PlayerCollectItemEvent> {
            if (it.item.item == Items.CLOCK) {
                startDelayedJob(it.player.asServerPlayer() ?: return@subscribeToEvent, advancement)
            }
        }
    }

    private fun removeEvents() {
        subscribeToEvent<PlayerDropItemEvent> {
            cancelJobForPlayer(it.player)
        }
    }

    private fun startDelayedJob(player: ServerPlayer, advancement: CompilableAdvancement): Job {
        val job = mcCoroutineTask(delay = 20.minutes) {
            if (player.inventory.items.any { it.item == Items.CLOCK }) {
                Advancements.awardAdvancement(player, advancement)
            }
        }

        playerJobMap[player] = job
        return job
    }

    private fun cancelJobForPlayer(player: ServerPlayer) {
        val job = playerJobMap[player] ?: return
        job.cancel()
        playerJobMap.remove(player)
    }
}