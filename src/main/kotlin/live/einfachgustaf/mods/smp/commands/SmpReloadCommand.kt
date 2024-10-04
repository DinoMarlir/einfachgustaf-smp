package live.einfachgustaf.mods.smp.commands

import live.einfachgustaf.mods.smp.internal.ReloadableRegistry
import net.kyori.adventure.text.Component
import net.silkmc.silk.commands.command

fun smpReloadCommand() = command("reload-smp") {
    // TODO: config for simpleName or name for argument and suggestion
    requiresPermissionLevel(4)

    runs {
        // Reload all reloadable objects
        ReloadableRegistry.reloadAll()
    }

    argument<String>("reloadable") { reloadableArgument ->
        suggestList {
            ReloadableRegistry.all().map { it::class.java.name }
        }

        runs {
            // Reload a specific reloadable object
            val reloadable = ReloadableRegistry.all().find { it::class.java.name == reloadableArgument.invoke(this) }
            if (reloadable != null) {
                reloadable.reload()
            } else {
                source.sendMessage(Component.text("Reloadable object not found"))
            }
        }
    }
}