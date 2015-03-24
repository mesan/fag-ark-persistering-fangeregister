package no.mesan.fag.arkitektur.persistering.fangerepo;

import io.dropwizard.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FangerepoConfiguration extends Configuration {
	@NotEmpty
	private String mongohost;

	@Min(1)
	@Max(65535)
	private int mongoport;

	@NotEmpty
	private String mongodb;

	@JsonProperty
	public String getMongohost() {
		return mongohost;
	}

	@JsonProperty
	public void setMongohost(String mongohost) {
		this.mongohost = mongohost;
	}
	

	@JsonProperty
	public int getMongoport() {
		return mongoport;
	}

	@JsonProperty
	public void setMongoport(int mongoport) {
		this.mongoport = mongoport;
	}

	@JsonProperty
	public String getMongodb() {
		return mongodb;
	}

	@JsonProperty
	public void setMongodb(String mongodb) {
		this.mongodb = mongodb;
	}
	
}