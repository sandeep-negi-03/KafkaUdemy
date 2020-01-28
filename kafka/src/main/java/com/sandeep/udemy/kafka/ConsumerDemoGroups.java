package com.sandeep.udemy.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemoGroups {

	private static Logger logger = LoggerFactory.getLogger(ConsumerDemoGroups.class.getName());

	public static void main(String[] args) {

		String bootstrapServers = "localhost:9092";
		String groupId = "my-fourth-application";
		String topic = "first_topic";

		/** Set Consumer Properties */
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // none, latest are other values

		/** Create Consumer */
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

		/** Subscribe Consumer to topic(s) */
		consumer.subscribe(Arrays.asList(topic));

		/** poll for the data */
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

			for (ConsumerRecord<String, String> record : records) {
				logger.info("Key : " + record.key() + "\n Value : " + record.value());
				logger.info("Partition : " + record.partition());
				logger.info("Offset : " + record.offset() + "\n");

			}
		}
		
	}
}
