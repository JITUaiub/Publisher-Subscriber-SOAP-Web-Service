package com.nittodigital.webservice.businesslogic;

import com.nittodigital.webservice.models.soap.production.ProductionModel;
import com.nittodigital.webservice.repository.ProductionRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Component
public class MQTTProductionCounter implements MqttCallback {

    @Autowired
    ProductionRepository productionRepository;

    private String mqttBroker = "tcp://iot.eclipse.org:1883";
    private String clientId = "production-counter";
    private String topic = "data";
    private CountDownLatch processingFinishedLatch;

    private MqttClient client;

    private String matchineNo;
    private String cardNo;
    public MQTTProductionCounter(){mqttSubscriberFetch();}

    public void mqttSubscriberFetch(){
        try{
            client = new MqttClient(mqttBroker, clientId);
            client.connect();
            client.setCallback(this);
            client.subscribe(topic);
            processingFinishedLatch = new CountDownLatch(1);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Received Message: " + message);
        String [] parts = new String(message.getPayload()).split("-");
        ProductionModel model = new ProductionModel();
        model.setMachineNo(parts[0]);
        model.setCardNo(parts[1]);
        model.setTimestamp(new Timestamp(new Date().getTime()));
        productionRepository.insertRecord(model);
        this.processingFinishedLatch.countDown();
        if(message.toString().equals("Processed")){
            MqttpublisherFetch("Processed", "operations/fetch");

        }
    }

    private void MqttpublisherFetch(String message, String topic){
        try {
            client = new MqttClient(mqttBroker, clientId);
            client.connect();

            createMqttMessage(message, topic);
            client.disconnect();
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    private void createMqttMessage(String message, String topic){
        try{
            MqttMessage publishMessage = new MqttMessage();
            publishMessage.setPayload(message.getBytes());
            client.publish(topic, publishMessage);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void waitForProcessingToFinish() throws InterruptedException{
        this.processingFinishedLatch.await();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
       // client.disconnect(topic);
    }

    @Override
    public void connectionLost(Throwable cause) {

    }
}
