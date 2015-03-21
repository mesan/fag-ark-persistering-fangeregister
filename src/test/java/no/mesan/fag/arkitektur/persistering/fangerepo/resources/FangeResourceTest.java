package no.mesan.fag.arkitektur.persistering.fangerepo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import io.dropwizard.testing.junit.ResourceTestRule;
import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;
import no.mesan.fag.arkitektur.persistering.fangerepo.resources.FangeResource;
import no.mesan.fag.arkitektur.persistering.fangerepo.store.FangeStore;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class FangeResourceTest {

	private static final FangeStore store = mock(FangeStore.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new FangeResource(store)).build();

	private final Fange fange = new Fange("123", "Gorm Hedløy");

	@Before
	public void setup() {
		when(store.getByName(eq("Gorm Hedløy"))).thenReturn(fange);
		when(store.getById(eq("123"))).thenReturn(fange);
	}
	
	@After
	public void tearDown() {
		reset(store);
	}

	@Test
	public void testGetFangeByName() {
		assertThat(resources.client().target(buildURL("navn=Gorm%20Hedløy")).request().get(Fange.class)).isEqualTo(fange);
		verify(store).getByName("Gorm Hedløy");
	}

	@Test
	public void testGetFangeById() {
		assertThat(resources.client().target(buildURL("id=123")).request().get(Fange.class)).isEqualTo(fange);
		verify(store).getById("123");
	}

	@Test
	public void testGetFangeByIdAndName() {
		assertThat(resources.client().target(buildURL("id=123&navn=Gorm%20Hedløy")).request().get(Fange.class)).isEqualTo(fange);
		verify(store).getById("123");
		verify(store, times(0)).getByName("Gorm Hedløy");
	}

	private String buildURL(String queryParameters) {
		return FangeResource.URL + "?" + queryParameters;
	}
}