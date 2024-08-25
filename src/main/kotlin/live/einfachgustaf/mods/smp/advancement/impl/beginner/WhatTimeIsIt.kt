package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object WhatTimeIsIt {

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
            x = 1.5f
        )

        ServerPlayConnectionEvents.JOIN.register { event, _, _ ->
            val player = event.player
            mcCoroutineScope.launch {
                while (!player.inventory.contains(Items.CLOCK.defaultInstance)) {
                    delay(1000)
                }

                val startTime = System.currentTimeMillis()
                var hasClockFor20Minutes = false

                while (!hasClockFor20Minutes) {
                    if (!player.inventory.contains(Items.CLOCK.defaultInstance)) {
                        break
                    }

                    val elapsedTime = System.currentTimeMillis() - startTime
                    if (elapsedTime >= 20 * 60 * 1000) {
                        hasClockFor20Minutes = true
                    }

                    delay(1000)
                }

                if (hasClockFor20Minutes) {
                    Advancements.awardAdvancement(player, advancement)
                }
            }
        }
    }
}
