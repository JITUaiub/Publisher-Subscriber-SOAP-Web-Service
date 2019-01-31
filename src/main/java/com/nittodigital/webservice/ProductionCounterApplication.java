package com.nittodigital.webservice;

import com.nittodigital.webservice.businesslogic.MQTTProductionCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.nittodigital.webservice.repository"})
public class ProductionCounterApplication {

	@Autowired
	private MQTTProductionCounter mqttProductionCounter;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		mqttProductionCounter.mqttSubscriberFetch();

		try{
			mqttProductionCounter.waitForProcessingToFinish();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(ProductionCounterApplication.class, args);
	}

}

