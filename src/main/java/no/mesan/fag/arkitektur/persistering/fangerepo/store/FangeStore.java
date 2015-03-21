package no.mesan.fag.arkitektur.persistering.fangerepo.store;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;

public interface FangeStore {

	public Fange getById(String id);
	public Fange getByName(String navn);

}
