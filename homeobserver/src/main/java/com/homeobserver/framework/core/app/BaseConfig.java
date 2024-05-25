package com.homeobserver.framework.core.app;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

public class BaseConfig {

    @Value("${sdk.observe.service.name}")
	@Getter @Setter private String serviceName;

	@Value("${sdk.observe.service.exporter.url}")
	@Getter @Setter private String otlpEndpoint;

	@Value("${sdk.observe.service.exporter.url.token}")
	@Getter @Setter private String secretToken;

}
