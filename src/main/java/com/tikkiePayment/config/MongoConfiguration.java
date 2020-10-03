package com.tikkiepayment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

	private static Logger LOG = LoggerFactory.getLogger(MongoConfiguration.class);

	@Value("${mongodb.URL}")
	private String mongoURL;

	@Value("${mongodb.databasename}")
	private String databaseName;


	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public MongoClient mongoClient() {
		LOG.info("Creating mongo client");
		ConnectionString connectionString = new ConnectionString(mongoURL);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        
        return MongoClients.create(mongoClientSettings);

	}

}
