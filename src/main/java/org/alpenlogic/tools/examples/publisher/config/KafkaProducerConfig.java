package org.alpenlogic.tools.examples.publisher.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProducer().getBootstrapServers());
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer());
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getValueSerializer());
		configProps.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getProducer().getRetries());
		configProps.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());
		configProps.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());
		configProps.put("key.converter.schema.registry.url", "http://localhost:8081");
		configProps.put("value.converter.schema.registry.url", "http://localhost:8081");

		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}