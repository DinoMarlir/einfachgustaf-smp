package live.einfachgustaf.mods.smp.utils

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import java.io.File
import java.nio.file.Path

/**
 * This class provides a way to manage configuration data with caching capabilities.
 * It uses JSON for serialization and deserialization of the data.
 *
 * @param V The type of the data to be managed.
 * @param path The path to the file where the data is stored.
 * @param default The default value to be used when the file does not exist.
 * @param serializer The serializer to be used for converting the data to JSON.
 * @param deserializer The deserializer to be used for converting JSON back to data.
 */
abstract class AbstractCachedConfig<V>(
    private val path: Path,
    private val default: V,
    private val serializer: SerializationStrategy<V>,
    private val deserializer: DeserializationStrategy<V>
) {
    private var cachedData: V? = null

    /**
     * Returns the file where the data is stored, creating it if it does not exist.
     */
    private val file: File get() {
        val toFile = path.toFile()

        if (!toFile.exists()) {
            toFile.parentFile.mkdirs()
            toFile.writeText(json.encodeToString(serializer, default))
        }
        return toFile
    }

    /**
     * Returns the cached data if it exists, null otherwise.
     */
    fun getCachedData(): V? {
        return cachedData
    }

    /**
     * Returns the data, either from cache or from the file.
     * If the data is not in cache, it is read from the file, deserialized and cached.
     *
     * @param cached If true, the data is returned from cache if it exists. If false, the data is always read from the file.
     */
    fun get(cached: Boolean = true): V {
        if (cached && cachedData != null) return cachedData!!
        val text = file.readText()
        val deserializedData = json.decodeFromString(deserializer, text)

        cachedData = deserializedData
        return cachedData!!
    }

    /**
     * Sets the cached data to the given value.
     * If write is true, the data is also serialized and written to the file.
     *
     * @param value The new value for the data.
     * @param write If true, the data is also written to the file.
     */
    fun set(value: V, write: Boolean = false): V {
        cachedData = value

        if (write) file.writeText(json.encodeToString(serializer, value))
        return value
    }

    /**
     * Writes the cached data to the file.
     * If there is no cached data, the default value is written to the file.
     */
    fun push(): V {
        file.writeText(json.encodeToString(serializer, cachedData ?: return default))
        return cachedData ?: default
    }
}