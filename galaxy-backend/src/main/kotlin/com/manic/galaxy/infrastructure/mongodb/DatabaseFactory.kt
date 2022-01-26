package com.manic.galaxy.infrastructure.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.UuidRepresentation
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.Convention
import org.bson.codecs.pojo.Conventions
import org.bson.codecs.pojo.PojoCodecProvider


object DatabaseFactory {

    fun new(uri: String): MongoDatabase {
        val connectionString = ConnectionString(uri)

        val conventions = listOf<Convention>(
            Conventions.ANNOTATION_CONVENTION,
            Conventions.SET_PRIVATE_FIELDS_CONVENTION,
            Conventions.CLASS_AND_PROPERTY_CONVENTION
        )
        val pojoCodecRegistry: CodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder()
                .conventions(conventions)
                .register("com.manic.galaxy.domain")
                .automatic(true)
                .build())
        )
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .codecRegistry(pojoCodecRegistry)
            .build()
        val mongoClient = MongoClients.create(settings)

        return mongoClient.getDatabase(connectionString.database)
    }
}