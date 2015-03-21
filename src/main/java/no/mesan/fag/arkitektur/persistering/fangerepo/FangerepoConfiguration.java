package no.mesan.fag.arkitektur.persistering.fangerepo;

import io.dropwizard.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FangerepoConfiguration extends Configuration {
	@NotEmpty
	private String dummy;

	@JsonProperty
	public String getDummy() {
		return dummy;
	}

	@JsonProperty
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}
}