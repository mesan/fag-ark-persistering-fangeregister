package no.mesan.fag.arkitektur.persistering.fangerepo.core;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

public class Fange {
	public static final String PROPERTY_ID = "id";
	public static final String PROPERTY_NAVN = "navn";

	@NotEmpty
	private final String id;

	@NotNull
	private final String navn;

	public Fange(String navn) {
		this(null, navn);
	}

	@JsonCreator
	private Fange(@JsonProperty(PROPERTY_ID) String id, @JsonProperty(PROPERTY_NAVN) String navn) {
		this.id = id;
		this.navn = optional(navn, "");
	}

	private <T> T optional(T string, T defaultValue) {
		return Optional.of(string).or(defaultValue);
	}

	@JsonProperty(PROPERTY_ID)
	public String getId() {
		return id;
	}

	@JsonProperty(PROPERTY_NAVN)
	public String getNavn() {
		return navn;
	}

	public Fange cloneWithId(String id) {
		return new Fange(id, navn);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((navn == null) ? 0 : navn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fange other = (Fange) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (navn == null) {
			if (other.navn != null)
				return false;
		} else if (!navn.equals(other.navn))
			return false;
		return true;
	}
}