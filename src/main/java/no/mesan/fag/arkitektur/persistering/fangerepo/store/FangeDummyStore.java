package no.mesan.fag.arkitektur.persistering.fangerepo.store;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;

public class FangeDummyStore implements FangeStore {

	@Override
	public Fange getByName(String navn) {
		return new Fange(navn).cloneWithId("" + Math.abs(new Random().nextInt()));
	}

	@Override
	public Fange getById(String id) {
		return new Fange("Dummy").cloneWithId(id);
	}

	@Override
	public Fange create(Fange fange) {
		return fange.cloneWithId("" + Math.abs(new Random().nextInt()));
	}

	@Override
	public Fange update(Fange fange) {
		return fange;
	}

	@Override
	public List<Fange> getAll() {
		return Arrays.asList(getByName("Dummy"));
	}
}