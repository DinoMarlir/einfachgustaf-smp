package live.einfachgustaf.mods.smp.polymer

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils
import live.einfachgustaf.mods.smp.LOGGER

/**
 * Registry for all resource pack components
 */
object PolymerResourceRegistry {

    /**
     * Registers all resource pack components
     */
    init {
        // mark resource pack as required
        PolymerResourcePackUtils.markAsRequired()

        // add mod assets
        if (PolymerResourcePackUtils.addModAssets("smp")) {
            LOGGER.info("Added assets to Polymer")
        } else {
            LOGGER.error("Failed to add assets to Polymer")
        }
    }
}