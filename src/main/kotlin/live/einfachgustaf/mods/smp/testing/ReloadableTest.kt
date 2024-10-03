package live.einfachgustaf.mods.smp.testing

import live.einfachgustaf.mods.smp.internal.Reloadable
import live.einfachgustaf.mods.smp.internal.ReloadableRegistry

object MyReloadable: Reloadable {

    override fun reload() {
        println("Reloaded!")
    }

    fun register() {
        // Register the reloadable object
        ReloadableRegistry.register(this)
    }
}