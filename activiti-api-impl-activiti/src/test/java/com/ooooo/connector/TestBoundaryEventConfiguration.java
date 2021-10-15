package com.ooooo.connector;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.runtime.connector.Connector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class TestBoundaryEventConfiguration {
	
	private final AtomicInteger count = new AtomicInteger(0);
	
	@Bean
	public Connector test1Connector() {
		return integrationContext -> {
			log.info("--- do executing test1Connector ---");
			int exception_cnt = (int) integrationContext.getInBoundVariables().getOrDefault("exception_cnt", 0);
			
			int cnt = count.incrementAndGet();
			if (cnt <= exception_cnt) {
				log.info("exception, cnt: {}", cnt);
				throw new RuntimeException("executing test1Connector failure");
			}
			
			return integrationContext;
		};
	}
	
	@Bean
	public Connector test2Connector() {
		return integrationContext -> {
			log.info("--- do executing test2Connector ---");
			
			return integrationContext;
		};
	}
	
	
	@Bean
	public Connector test3Connector() {
		return integrationContext -> {
			log.info("--- do executing test3Connector ---");
			
			return integrationContext;
		};
	}
}
