package org.producer.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.producer.dto.MessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaProducerConfig {

    private static final String TOPIC_NAME = "delay-transfer-topic";

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094"); // Kafka 서버 주소
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // 데이터 안정성을 위한 설정
        props.put(ProducerConfig.RETRIES_CONFIG, 3); // 재시도 횟수
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1); // 배치 전송 지연 시간

        return props;
    }



    @Bean
    public ProducerFactory<String, MessageDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, MessageDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public org.apache.kafka.clients.admin.NewTopic topic() {
        return TopicBuilder.name(TOPIC_NAME)
                .partitions(3) // 파티션 수
                .replicas(3) // 복제본 수
                .build();
    }
}