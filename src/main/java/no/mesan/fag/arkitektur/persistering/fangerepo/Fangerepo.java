package no.mesan.fag.arkitektur.persistering.fangerepo;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import no.mesan.fag.arkitektur.persistering.fangerepo.health.ConfigurationHealthCheck;
import no.mesan.fag.arkitektur.persistering.fangerepo.resources.FangeResource;
import no.mesan.fag.arkitektur.persistering.fangerepo.store.FangeDummyStore;

public class Fangerepo extends Application<FangerepoConfiguration> {
	public static void main(String[] args) throws Exception {
		new Fangerepo().run(args);
	}

	@Override
	public String getName() {
		return "Mesan fangerepo";
	}

	@Override
	public void run(FangerepoConfiguration configuration, Environment environment) {
		registerHealthChecks(configuration, environment);
		registerResources(environment);
	}

	private void registerHealthChecks(FangerepoConfiguration configuration, Environment environment) {
		final ConfigurationHealthCheck healthCheck = new ConfigurationHealthCheck(configuration);
		environment.healthChecks().register("configuration", healthCheck);
	}

	private void registerResources(Environment environment) {
		environment.jersey().register(new FangeResource(new FangeDummyStore()));
	}

}