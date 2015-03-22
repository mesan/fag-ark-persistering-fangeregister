package no.mesan.fag.arkitektur.persistering.fangerepo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
	public void testGetFangeByName() {
		assertThat(resources.client().target(buildURL(navn("Gorm Hedløy"))).request().get(Fange.class)).isEqualTo(fange);
		verify(store).getByName("Gorm Hedløy");
	}

	@Test
	public void testGetFangeById() {
		assertThat(resources.client().target(buildURL(id("123"))).request().get(Fange.class)).isEqualTo(fange);
		verify(store).getById("123");
	}

	@Test
	public void testGetFangeByIdAndName() {
		assertThat(resources.client().target(buildURL(query(id("123"), navn("Gorm Hedløy")))).request().get(Fange.class)).isEqualTo(fange);
		verify(store).getById("123");
		verify(store, times(0)).getByName("Gorm Hedløy");
	}
	
	@Test
	public void testCreateFange() {
		String json = "{" + jsonString(Fange.PROPERTY_NAVN) + ":" + jsonString("Gorm Hedløy") + "}";
		Response inboundResponse = resources.client().target(FangeResource.URL).request().post(Entity.json(json));
		Response outboundResponse = FangeResource.createCreatedResponse(fange);

		assertThat(inboundResponse.getStatus()).isEqualTo(outboundResponse.getStatus());
		assertThat(inboundResponse.readEntity(Fange.class)).isEqualTo(fange);
		
		verify(store).create(new Fange("Gorm Hedløy"));
	}

	@Test
	public void testUpdateFange() {
		String json = "{" + jsonString(Fange.PROPERTY_ID) + ":" + "123" + "," + jsonString(Fange.PROPERTY_NAVN) + ":" + jsonString("Gorm Hedløy") + "}";
		Response inboundResponse = resources.client().target(FangeResource.URL).request().put(Entity.json(json));
		Response outboundResponse = FangeResource.createUpdatedResponse(fange);

		assertThat(inboundResponse.getStatus()).isEqualTo(outboundResponse.getStatus());
		assertThat(inboundResponse.readEntity(Fange.class)).isEqualTo(fange);
		verify(store).update(fange);
	}

	private String jsonString(String stringContent) {
		return "\"" + stringContent + "\"";
	}
	
	private String buildURL(String queryParameters) {
		if (queryParameters.isEmpty()) {
			return FangeResource.URL;
		}
		return FangeResource.URL + "?" + queryParameters;
	}

	private String query(String... params) {
		if (params.length > 0) {
			String queryString = params[0];
	
			for (int i = 1; i < params.length; i++) {
				queryString += "&" + params[i];
			}
			return queryString;
		}
		return "";
	}

	private String id(String string) {
		return Fange.PROPERTY_ID + "=" + string;
	}

	private String navn(String string) {
		try {
			return Fange.PROPERTY_NAVN + "=" + URLEncoder.encode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return Fange.PROPERTY_NAVN + "=" + string;
		}
	}
}