package com.kumar.config;

import java.util.HashMap;
import java.util.Map;

import com.kumar.domain.generated.Employee;
import com.kumar.domain.generated.WebRoomEventAvro;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;


import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class EmployeeEventsConsumerConfig {

	@Bean
	public ConsumerFactory<Integer, String> ConsumerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093, localhost:9094");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "person-events-listner-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(ConsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<Integer, Employee> employeeConsumerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093, localhost:9094");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "employee-events-listner-group1");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
		config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
		return new DefaultKafkaConsumerFactory(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, Employee> employeeKafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, Employee> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(employeeConsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, WebRoomEventAvro> webRoomConsumerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093, localhost:9094");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "webRoom-events-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
		config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
		return new DefaultKafkaConsumerFactory(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, WebRoomEventAvro> webRoomKafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, WebRoomEventAvro> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(webRoomConsumerFactory());
		return factory;
	}
}
