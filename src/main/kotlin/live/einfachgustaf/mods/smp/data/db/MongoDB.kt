package live.einfachgustaf.mods.smp.data.db

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.CompilableAdvancement
import live.einfachgustaf.mods.smp.data.PlayerAdvancementData
import net.minecraft.resources.ResourceLocation
import net.silkmc.silk.core.task.mcCoroutineScope

@Suppress("unused")
object MongoDB {

    private val mongoClient = MongoClient.create(System.getenv("MONGODB_URL"))
    private val database = mongoClient.getDatabase(System.getenv("MONGODB_DATABASE"))
    private val advancementsCollection = database.getCollection<PlayerAdvancementData>("PLAYER_ADVANCEMENTS")

    fun awardAdvancement(uuid: String, advancement: CompilableAdvancement) {
        mcCoroutineScope.launch {
            val advancements = getPlayerAdvancements(uuid).toMutableSet()
            advancements += advancement
            upsert(uuid, PlayerAdvancementData(uuid, advancements.map { it.id.toString() }.toSet()))
        }
    }

    fun revokeAdvancement(uuid: String, advancement: CompilableAdvancement) {
        mcCoroutineScope.launch {
            val advancements = getPlayerAdvancements(uuid).toMutableSet()
            advancements -= advancement
            upsert(uuid, PlayerAdvancementData(uuid, advancements.map { it.id.toString() }.toSet()))
        }
    }

    suspend fun getPlayerAdvancements(uuid: String): Set<CompilableAdvancement> {
        val player = advancementsCollection.find(Filters.eq("uuid", uuid)).firstOrNull() ?: return emptySet()
        return player.advancements.mapNotNull { Advancements.advancement(ResourceLocation(it)) }.toSet()
    }

    private suspend fun upsert(uuid: String, data: PlayerAdvancementData) {
        if (advancementsCollection.find(Filters.eq("uuid", uuid)).firstOrNull() == null) {
            advancementsCollection.insertOne(data)
        } else {
            advancementsCollection.replaceOne(Filters.eq("uuid", uuid), data)
        }
    }

}