package no.mesan.fag.arkitektur.persistering.fangerepo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;
import no.mesan.fag.arkitektur.persistering.fangerepo.store.FangeStore;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class FangeResourceTest {

	private static final FangeStore store = mock(FangeStore.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new FangeResource(store)).build();

	private final Fange fange = new Fange("Gorm Hedløy").cloneWithId("123");

	@Before
	public void setup() {
		when(store.getByName(eq("Gorm Hedløy"))).thenReturn(fange);
		when(store.getById(eq("123"))).thenReturn(fange);
		when(store.create((eq(new Fange("Gorm Hedløy"))))).thenReturn(fange);
		when(store.update((eq(fange)))).thenReturn(fange);
	}
	
	@After
	public void tearDown() {
		reset(store);
	}

	@Test
	public void testGetFangeById() {
		assertThat(resources.client().target(buildURL("123")).request().get(Fange.class)).isEqualTo(fange);
		verify(store).getById("123");
	}

	@Test
	public void testCreateFange() {
		String json = "{" + jsonString(Fange.PROPERTY_NAVN) + ":" + jsonString("Gorm Hedløy") + "}";
		Response inboundResponse = resources.client().target(buildURL()).request().post(Entity.json(json));
		Response outboundResponse = FangeResource.createCreatedResponse(fange);

		assertThat(inboundResponse.getStatus()).isEqualTo(outboundResponse.getStatus());
		assertThat(inboundResponse.readEntity(Fange.class)).isEqualTo(fange);
		
		verify(store).create(new Fange("Gorm Hedløy"));
	}

	@Test
	public void testUpdateFange() {
		String json = "{" + jsonString(Fange.PROPERTY_ID) + ":" + "123" + "," + jsonString(Fange.PROPERTY_NAVN) + ":" + jsonString("Gorm Hedløy") + "}";
		Response inboundResponse = resources.client().target(buildURL()).request().put(Entity.json(json));
		Response outboundResponse = FangeResource.createUpdatedResponse(fange);

		assertThat(inboundResponse.getStatus()).isEqualTo(outboundResponse.getStatus());
		assertThat(inboundResponse.readEntity(Fange.class)).isEqualTo(fange);
		verify(store).update(fange);
	}

	private String jsonString(String stringContent) {
		return "\"" + stringContent + "\"";
	}
	
	private String buildURL() {
		return buildURL("");
	}

	private String buildURL(String id) {
		return "/" + FangeResource.PATH + (id.isEmpty() ? "" : "/" + id);
	}
}