package live.einfachgustaf.mods.smp

import live.einfachgustaf.mods.smp.advancement.Advancements
import net.minecraft.advancements.*
import net.minecraft.advancements.critereon.PlayerTrigger
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import java.time.Instant
import java.util.*

/**
 * Sends a notification toast to the player
 * @param title - Title of the notifcation. Won't be shown in  the toast.
 * @param description - Content of the notification
 * @param icon - Item to be shown as icon
 */
fun ServerPlayer.sendNotifcation(title: Component, description: Component, icon: ItemStack) {
   val advancement=
        Advancement.Builder.advancement()
            .display(DisplayInfo(
                icon,
                title,
                description,
                Optional.of(Advancements.DEFAULT_RESOURCE),
                AdvancementType.TASK,
                true,
                true,
                true
            ))
            .addCriterion("dummy", PlayerTrigger.TriggerInstance.tick())
            .build(ResourceLocation("einfachgustaf:/root/dummy"))

    val progress = AdvancementProgress()

    val requirements = AdvancementRequirements.allOf(listOf("dummy"))
    progress.update(requirements)
    progress.grantProgress("dummy")

    connection.send(
        ClientboundUpdateAdvancementsPacket(
            false,
            listOf(advancement),
            setOf(),
            mapOf(advancement.id to progress)
        )
    )
    LOGGER.info("Sent packet")
}