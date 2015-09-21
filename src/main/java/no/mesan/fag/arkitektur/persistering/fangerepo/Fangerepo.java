package no.mesan.fag.arkitektur.persistering.fangerepo;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.mesan.fag.arkitektur.persistering.fangerepo.health.ConfigurationHealthCheck;
import no.mesan.fag.arkitektur.persistering.fangerepo.resources.FangeResource;
import no.mesan.fag.arkitektur.persistering.fangerepo.store.FangeMongoDBMorphiaStore;

public class Fangerepo extends Application<FangerepoConfiguration> {
	public static void main(String[] args) throws Exception {
		new Fangerepo().run(args);
	}

	@Override
	public String getName() {
		return "Mesan fangerepo";
	}

	@Override
	public void initialize(Bootstrap<FangerepoConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor()));
	}

	@Override
	public void run(FangerepoConfiguration configuration, Environment environment) {
		registerHealthChecks(configuration, environment);
		registerResources(configuration, environment);
	}

	private void registerHealthChecks(FangerepoConfiguration configuration, Environment environment) {
		final ConfigurationHealthCheck healthCheck = new ConfigurationHealthCheck(configuration);
		environment.healthChecks().register("configuration", healthCheck);
	}

	private void registerResources(FangerepoConfiguration configuration, Environment environment) {
		environment.jersey().register(new FangeResource(new FangeMongoDBMorphiaStore(configuration)));
	}

}