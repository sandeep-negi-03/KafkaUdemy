package com.sandeep.udemy.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class ProducerWithKeys {

	private static Logger logger = LoggerFactory.getLogger(ProducerWithKeys.class);

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		/** Step 1 Create producer properties */
		Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		/** Step 2 Create producer */
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		for (int i = 0; i < 5; i++) {

			String topic  = "first_topic";
			String value = "Hello value from Producer With Keys";
			String key = "id_" + Integer.toString(i);
			
			/** Step 3 Create producer record */
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic , key, value);

			logger.info("key : " + key);
			// id_0 partition 1
			// id_1 partition 0
			// id_2 partition 2
			// id_3 partition 0
			// id_4 partition 2
			
			/** Step 4 Send Data Asynchronous */
			producer.send(record, new Callback() {

				public void onCompletion(RecordMetadata metadata, Exception exception) {
					/**
					 * executes everytime a record is sent successfully or an execption is occurred
					 */
					if (exception == null) {
						logger.info("Received new metadata. \n" + "Topic : " + metadata.topic() + "\n" + "Partition : "
								+ metadata.partition() + "\n" + "Offset : " + metadata.offset() + "\n" + "Timestamp : "
								+ metadata.timestamp());
					} else {
						logger.error("Error while producing " + exception);
					}

				}
			}).get();	//block the send to make it synchronous
		}

		/** Flush the data */
		producer.flush();

		/** Flush and close the data stream */
		producer.close();

	}
}
