package live.einfachgustaf.mods.smp

import live.einfachgustaf.mods.smp.features.StatusFeature
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager

object Entrypoint: ModInitializer, DedicatedServerModInitializer {

    val logger = LogManager.getLogger("smp")
    
    override fun onInitialize() {
        StatusFeature
    }

    override fun onInitializeServer() {
        // Dedicated server initialization
    }
}