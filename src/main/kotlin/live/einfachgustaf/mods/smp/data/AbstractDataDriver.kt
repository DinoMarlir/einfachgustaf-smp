package live.einfachgustaf.mods.smp.data

import live.einfachgustaf.mods.smp.advancement.CompilableAdvancement

/**
 * Abstract class representing a data driver for managing player advancements.
 */
abstract class AbstractDataDriver {

    /**
     * Awards an advancement to a player.
     *
     * @param uuid The UUID of the player.
     * @param advancement The advancement to be awarded.
     */
    abstract fun awardAdvancement(uuid: String, advancement: CompilableAdvancement)

    /**
     * Revokes an advancement from a player.
     *
     * @param uuid The UUID of the player.
     * @param advancement The advancement to be revoked.
     */
    abstract fun revokeAdvancement(uuid: String, advancement: CompilableAdvancement)

    /**
     * Retrieves the set of advancements a player has.
     *
     * @param uuid The UUID of the player.
     * @return A set of advancements the player has.
     */
    abstract suspend fun getPlayerAdvancements(uuid: String): Set<CompilableAdvancement>

}