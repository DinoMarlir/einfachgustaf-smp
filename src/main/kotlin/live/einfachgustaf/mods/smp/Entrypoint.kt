package live.einfachgustaf.mods.smp

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.commands.arguments.item.ItemInput
import net.minecraft.data.registries.VanillaRegistries
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.literalText
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val LOGGER: Logger = LogManager.getLogger("smp")
    
fun initMain() {
}

fun initServer() {
    val context = Commands.createValidationContext(VanillaRegistries.createLookup())
    command("notify") {
        argument<ItemInput>("item", ItemArgument.item(context)) { item ->
            argument<String>("text") { text ->
                runs {
                    source.playerOrException.sendNotifcation(literalText(text.invoke(this)), item.invoke(this).createItemStack(1, false))
                }
            }
        }
    }

}