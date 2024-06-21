package live.einfachgustaf.mods.smp.advancement.impl

import live.einfachgustaf.mods.smp.advancement.AbstractAdvancement
import live.einfachgustaf.mods.smp.advancement.annontations.Advancement

@Advancement(
    "First Join"
)
class FirstJoinAdvancement(val time: Long): AbstractAdvancement()