package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object Talkactive {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.SKULL_BANNER_PATTERN.defaultInstance,
                literalText("Talkactive!"),
                literalText("Schreib etwas in den Chat"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = emptySet()
            ),
            "talkactive",
            beginnerRoot,
            x = 1.5f * 4
        )

        ServerMessageEvents.CHAT_MESSAGE.register { _, serverPlayer, _ ->
            mcCoroutineScope.launch {
                Advancements.awardAdvancement(serverPlayer, advancement)
            }
        }
    }
}