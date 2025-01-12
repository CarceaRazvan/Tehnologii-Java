package lab12;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@ApplicationScoped
public class ConsumerService {

    private static final String SERVER = "localhost:9092";  // Kafka Broker address
    private static final String TOPIC = "quickstart-events";  // Kafka Topic
    private static final String GROUP_ID = "microservice-b-group";  // Consumer group ID

    @PostConstruct
    public void startConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(TOPIC));

            while (true) {
                var records = consumer.poll(Duration.ofMillis(1000));  // Poll for new messages
                records.forEach(record -> {
                    System.out.println("Consumed message: " + record.value());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
