package no.mesan.fag.arkitektur.persistering.fangerepo.resources;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;
import no.mesan.fag.arkitektur.persistering.fangerepo.store.FangeStore;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path(FangeResource.URL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FangeResource {
	public static final String URL = "/fange";

	private final FangeStore store;

	public FangeResource(FangeStore store) {
		this.store = store;
	}

	@GET
	@Timed
	public Fange get(@QueryParam(Fange.PROPERTY_ID) Optional<String> id, @QueryParam(Fange.PROPERTY_NAVN) Optional<String> navn) {
		if (id.isPresent()) {
			return store.getById(id.get().trim());
		} else {
			return store.getByName(navn.or("").trim());
		}
	}
	
	@GET
	@Path("/alle")
	@Timed
	public List<Fange> getAll() {
		return store.getAll();
	}
	
	@POST
	@Timed
	public Response create(Fange fange) {
		Fange newFange = store.create(fange);
		return createCreatedResponse(newFange);
	}

	static Response createCreatedResponse(Fange fange) {
		return Response.status(Response.Status.CREATED).entity(fange).build();
	}
	
	@PUT
	@Timed
	public Response update(@Valid Fange fange) {
		Fange updatedFange = store.update(fange);
		return createUpdatedResponse(updatedFange);
	}

	static Response createUpdatedResponse(Fange fange) {
		return Response.accepted(fange).build();
	}
}