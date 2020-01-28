package com.sandeep.udemy.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Hello world!
 *
 */
public class ProducerWithCallbacks 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        /** Step 1  Create producer properties*/
        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        /** Step 2  Create producer */
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        
        /** Step 3 Create producer record */
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "Hello First Program");
        
        /** Step 4 Send Data */
        producer.send(record);
        
        /**	Flush the data */
        producer.flush();
        
        /** Flush and close the data stream*/
        producer.close();
        
    }
}
