package live.einfachgustaf.mods.smp.data.player

import com.mongodb.client.model.Filters
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.data.db.MongoDB
import net.silkmc.silk.core.task.mcCoroutineScope
import java.util.UUID

object PlayerRepository {
    private val collection = MongoDB.database.getCollection<SMPPlayerModel>("players")

    fun createPlayer(uuid: UUID) {
        mcCoroutineScope.launch {
            collection.insertOne(SMPPlayerModel(uuid.toString()))
        }
    }

    suspend fun getPlayer(uuid: UUID): SMPPlayerModel? {
        return collection.find(Filters.eq(SMPPlayerModel::uuid.name, uuid.toString())).firstOrNull()
    }

    suspend fun getPlayerOrCreate(uuid: UUID): SMPPlayerModel {
        if (getPlayer(uuid) == null) {
            createPlayer(uuid)
            return SMPPlayerModel(uuid.toString())
        } else {
            return getPlayer(uuid)!!
        }
    }

    suspend fun updatePlayer(player: SMPPlayerModel) {
        if (getPlayer(UUID.fromString(player.uuid)) == null)
            createPlayer(UUID.fromString(player.uuid))
        else
            collection.replaceOne(Filters.eq(SMPPlayerModel::uuid.name, player.uuid), player)
    }
}