package no.mesan.fag.arkitektur.persistering.fangerepo.health;

import io.dropwizard.Configuration;
import no.mesan.fag.arkitektur.persistering.fangerepo.FangerepoConfiguration;

import com.codahale.metrics.health.HealthCheck;

public class ConfigurationHealthCheck extends HealthCheck {
	private final FangerepoConfiguration configuration;

	public ConfigurationHealthCheck(FangerepoConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected Result check() throws Exception {
		if (!(configuration instanceof Configuration)) {
			return Result.unhealthy("The configuration is not a Dropwizard configuration");
		}
		return Result.healthy();
	}
}