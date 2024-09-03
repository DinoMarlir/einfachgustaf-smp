package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.CandleLightUpEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object BirthdayParty {

    private val candleCakes = setOf(
        Blocks.CANDLE_CAKE,
        Blocks.WHITE_CANDLE_CAKE,
        Blocks.ORANGE_CANDLE_CAKE,
        Blocks.MAGENTA_CANDLE_CAKE,
        Blocks.LIGHT_BLUE_CANDLE_CAKE,
        Blocks.YELLOW_CANDLE_CAKE,
        Blocks.LIME_CANDLE_CAKE,
        Blocks.PINK_CANDLE_CAKE,
        Blocks.GRAY_CANDLE_CAKE,
        Blocks.LIGHT_GRAY_CANDLE_CAKE,
        Blocks.CYAN_CANDLE_CAKE,
        Blocks.PURPLE_CANDLE_CAKE,
        Blocks.BLUE_CANDLE_CAKE,
        Blocks.BROWN_CANDLE_CAKE,
        Blocks.GREEN_CANDLE_CAKE,
        Blocks.RED_CANDLE_CAKE,
        Blocks.BLACK_CANDLE_CAKE
    )

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.CAKE.defaultInstance,
                literalText("Birthday Party"),
                literalText("Platziere eine Kerze auf einem Kuchen und zünde sie an"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    Items.CANDLE.defaultInstance
                )
            ), "birthdayparty", beginnerRoot, x = 1.5f * 4, y = 2 * 1.5f
        )

        subscribeToEvent<CandleLightUpEvent> { event ->
            if (candleCakes.contains(event.blockState.block)) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }

}