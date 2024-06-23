package live.einfachgustaf.mods.smp

import live.einfachgustaf.mods.smp.advancement.Advancements
import net.minecraft.advancements.*
import net.minecraft.advancements.critereon.PlayerTrigger
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.silkmc.silk.core.text.literalText
import java.util.*

/**
 * Sends a notification toast to the player
 * @param content - Content of the notification
 * @param icon - Item to be shown as icon
 */
fun ServerPlayer.sendNotifcation(content: Component, icon: ItemStack, type: AdvancementType) {
   val advancement = Advancement.Builder.advancement()
        .display(DisplayInfo(
            icon,
            content,
            literalText("This should not appear ingame"),
            Optional.of(Advancements.DEFAULT_RESOURCE),
            type,
            true,
            false,
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
    LOGGER.debug("Sent advancement creation packet to $stringUUID")
    // Now remove the packet so it doesn't show up in the advancements screen
    connection.send(
        ClientboundUpdateAdvancementsPacket(
            false,
            listOf(),
            setOf(advancement.id),
            mapOf()
        )
    )
    LOGGER.debug("Sent advancement deletion packet to $stringUUID")
}