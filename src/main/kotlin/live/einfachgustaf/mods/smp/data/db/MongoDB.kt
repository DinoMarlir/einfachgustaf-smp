package live.einfachgustaf.mods.smp.data.db

import com.mongodb.kotlin.client.coroutine.MongoClient

object MongoDB {
    private val mongoClient = MongoClient.create(System.getenv("MONGODB_URL"))
    internal val database = mongoClient.getDatabase(System.getenv("MONGODB_DATABASE"))
}