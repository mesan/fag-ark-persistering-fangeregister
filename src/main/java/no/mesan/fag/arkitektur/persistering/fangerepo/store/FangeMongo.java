package no.mesan.fag.arkitektur.persistering.fangerepo.store;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;

@Entity(value="fanger", noClassnameStored=true)
public class FangeMongo {

	@Id
	private ObjectId id;

	private String navn;
	
	private FangeMongo() {
		// used by Morphia
	}

	public FangeMongo(Fange fange) {
		this.id = fange.getId() != null ? new ObjectId(fange.getId()) : null;
		this.navn = fange.getNavn();
	}

	public Fange toFange() {
		return new Fange(this);
	}

	public ObjectId getId() {
		return id;
	}

	public String getNavn() {
		return navn;
	}
}