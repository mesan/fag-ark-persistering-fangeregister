package no.mesan.fag.arkitektur.persistering.fangerepo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;
import no.mesan.fag.arkitektur.persistering.fangerepo.store.FangeStore;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path(FangeResource.URL)
@Produces(MediaType.APPLICATION_JSON)
public class FangeResource {
	public static final String URL = "/fange";

	private final FangeStore store;

	public FangeResource(FangeStore store) {
		this.store = store;
	}

	@GET
	@Timed
	public Fange getFange(@QueryParam("id") Optional<String> id, @QueryParam("navn") Optional<String> navn) {
		if (id.isPresent()) {
			return store.getById(id.get().trim());
		} else {
			return store.getByName(navn.or("").trim());
		}
	}
}