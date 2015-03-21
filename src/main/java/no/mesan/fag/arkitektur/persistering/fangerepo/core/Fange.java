package no.mesan.fag.arkitektur.persistering.fangerepo.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fange {
    private String id;

    private String navn;

    private Fange() {
        // Jackson deserialization
    }

    public Fange(String id, String navn) {
        this.id = id;
        this.navn = navn;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getNavn() {
        return navn;
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