package org.alpenlogic.tools.examples.publisher.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.alpenlogic.tools.examples.avro.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
	private final LocalKafkaProperties kprops;

	public KafkaProducerConfig(LocalKafkaProperties localKafkaProperties) {
		this.kprops = localKafkaProperties;
	}

	@Bean
	public ProducerFactory<String, Message> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kprops.getProducer().getBootstrapServers());
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kprops.getProducer().getKeySerializer());
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kprops.getProducer().getValueSerializer());
		configProps.put("schema.registry.url", kprops.getSchemaRegistryUrl());
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Message> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}