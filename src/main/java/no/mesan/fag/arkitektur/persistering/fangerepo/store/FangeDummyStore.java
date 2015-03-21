package no.mesan.fag.arkitektur.persistering.fangerepo.store;

import java.util.Random;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;

public class FangeDummyStore implements FangeStore {

	@Override
	public Fange getByName(String navn) {
		return new Fange("" + Math.abs(new Random().nextInt()), navn);
	}

	@Override
	public Fange getById(String id) {
		return new Fange(id, "Dummy");
	}
}