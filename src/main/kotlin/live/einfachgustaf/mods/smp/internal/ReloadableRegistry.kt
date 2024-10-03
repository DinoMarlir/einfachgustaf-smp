package live.einfachgustaf.mods.smp.internal

/**
 * Represents an object that can be reloaded
 */
object ReloadableRegistry {

    private val reloadables = mutableListOf<Reloadable>()

    /**
     * Registers a reloadable object
     */
    fun register(reloadable: Reloadable) {
        reloadables.add(reloadable)
    }

    /**
     * Reloads all registered objects
     */
    fun reloadAll() {
        reloadables.forEach { it.reload() }
    }

    /**
     * Gets all registered objects
     *
     * @return All registered objects
     */
    fun all() = reloadables
}