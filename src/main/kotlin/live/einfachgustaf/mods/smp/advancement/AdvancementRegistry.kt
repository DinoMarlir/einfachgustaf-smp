package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.impl.beginner.ALittleGift
import live.einfachgustaf.mods.smp.advancement.impl.beginner.TheBeginning

object AdvancementRegistry {

    val functions = setOf(
        TheBeginning::register,
        ALittleGift::register
    )

    init {
        val multiplier = 1.5F

        for (i in 1..functions.size) {
            functions.forEach { function ->
                function.invoke(multiplier * i)
            }
        }
    }
}