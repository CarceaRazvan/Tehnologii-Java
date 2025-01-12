package com.demo.rest.laboratorul12;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Path("/messageContinuous")
public class DataExporterService {

    private static final String SERVER = "localhost:9092";  // Kafka Broker address
    private static final String TOPIC = "quickstart-events";  // Kafka Topic for export

    @GET
    @Path("/start")
    public void startExport() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        System.out.println("Starting data export...");

        // Simulate continuous data generation and export
        while (true) {
            String simulatedData = "Exported Data: " + System.currentTimeMillis();
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "key", simulatedData);
            producer.send(record);
            System.out.println("Exported data: " + simulatedData);

            try {
                TimeUnit.SECONDS.sleep(1); // Simulate a delay between exports
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
