package no.mesan.fag.arkitektur.persistering.fangerepo.resources;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;
import no.mesan.fag.arkitektur.persistering.fangerepo.store.FangeStore;

import com.codahale.metrics.annotation.Timed;

@Path(FangeResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FangeResource {

	private final Logger logger = LoggerFactory.getLogger(FangeResource.class);

	public static final String PATH = "fanger";

	private final FangeStore store;

	public FangeResource(FangeStore store) {
		this.store = store;
	}

	@GET
	@Path("{id}")
	@Timed
	public Fange get(@PathParam("id") String id) {
		logger.info("Henter fange med id {}", id);
		return store.getById(id.trim());
	}

	@GET
	@Timed
	public List<Fange> getAll() {
		logger.info("Henter alle fanger");
		return store.getAll();
	}

	@POST
	@Timed
	public Response create(Fange fange) {
		logger.info("Oppretter fange med navn '{}'", fange.getNavn());
		return createCreatedResponse(store.create(fange));
	}

	static Response createCreatedResponse(Fange fange) {
		return Response.status(Response.Status.CREATED).entity(fange).build();
	}

	@PUT
	@Timed
	public Response update(@Valid Fange fange) {
		logger.info("Oppdaterer fange med id {}", fange.getId());
		return createUpdatedResponse(store.update(fange));
	}

	static Response createUpdatedResponse(Fange fange) {
		return Response.ok(fange).build();
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.WILDCARD)
	@Timed
	public Response delete(@PathParam("id") String id) {
		logger.info("Sletter fange med id {}", id);
		store.delete(id);
		return Response.ok().build();
	}
}