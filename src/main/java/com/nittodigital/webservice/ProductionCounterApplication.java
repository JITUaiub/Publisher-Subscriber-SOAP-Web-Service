package com.nittodigital.webservice;

import com.nittodigital.webservice.businesslogic.MQTTProductionCounter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.nittodigital.webservice.repository", "com.nittodigital.webservice.businesslogic"})
public class ProductionCounterApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProductionCounterApplication.class, args);

		MQTTProductionCounter mqttProductionCounter = new MQTTProductionCounter("tcp://iot.eclipse.org:1883", "production-counter", "data");
		mqttProductionCounter.mqttSubscriberFetch();

		try{
			mqttProductionCounter.waitForProcessingToFinish();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}

}

