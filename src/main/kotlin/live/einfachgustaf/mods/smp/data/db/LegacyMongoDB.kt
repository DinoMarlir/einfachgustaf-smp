package live.einfachgustaf.mods.smp.data.db

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.CompilableAdvancement
import live.einfachgustaf.mods.smp.data.AbstractDataDriver
import live.einfachgustaf.mods.smp.data.PlayerAdvancementData
import net.minecraft.resources.ResourceLocation
import net.silkmc.silk.core.task.mcCoroutineScope

object LegacyMongoDB : AbstractDataDriver() {

    private val mongoClient = MongoClient.create(System.getenv("MONGODB_URL"))
    private val database = mongoClient.getDatabase(System.getenv("MONGODB_DATABASE"))
    private val advancementsCollection = database.getCollection<PlayerAdvancementData>("PLAYER_ADVANCEMENTS")

    /**
     * Awards an advancement to a player.
     *
     * @param uuid The UUID of the player.
     * @param advancement The advancement to be awarded.
     */
    override fun awardAdvancement(uuid: String, advancement: CompilableAdvancement) {
        mcCoroutineScope.launch {
            val advancements = getPlayerAdvancements(uuid).toMutableSet()
            advancements += advancement
            upsert(uuid, PlayerAdvancementData(uuid, advancements.map { it.id.toString() }.toSet()))
        }
    }

    /**
     * Revokes an advancement from a player.
     *
     * @param uuid The UUID of the player.
     * @param advancement The advancement to be revoked.
     */
    override fun revokeAdvancement(uuid: String, advancement: CompilableAdvancement) {
        mcCoroutineScope.launch {
            val advancements = getPlayerAdvancements(uuid).toMutableSet()
            advancements -= advancement
            upsert(uuid, PlayerAdvancementData(uuid, advancements.map { it.id.toString() }.toSet()))
        }
    }

    /**
     * Retrieves the set of advancements a player has.
     *
     * @param uuid The UUID of the player.
     * @return A set of advancements the player has.
     */
    override suspend fun getPlayerAdvancements(uuid: String): Set<CompilableAdvancement> {
        val player = advancementsCollection.find(Filters.eq("uuid", uuid)).firstOrNull() ?: return emptySet()
        return player.advancements.mapNotNull { Advancements.advancement(ResourceLocation.parse(it)) }.toSet()
    }

    /**
     * Upserts the player advancement data in the database.
     *
     * @param uuid The UUID of the player.
     * @param data The player advancement data to be upserted.
     */
    private suspend fun upsert(uuid: String, data: PlayerAdvancementData) {
        if (advancementsCollection.find(Filters.eq("uuid", uuid)).firstOrNull() == null) {
            advancementsCollection.insertOne(data)
        } else {
            advancementsCollection.replaceOne(Filters.eq("uuid", uuid), data)
        }
    }
}