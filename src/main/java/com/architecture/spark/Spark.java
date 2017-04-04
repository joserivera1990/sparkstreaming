package com.architecture.spark;

import java.net.UnknownHostException;
import java.util.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.*;

import com.architecture.spark.services.Mongo;
import com.architecture.spark.services.impl.MongoImpl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import scala.Tuple2;

public class Spark {

	public static void main(String[] args) throws InterruptedException, UnknownHostException {
		
		Mongo mongo = new MongoImpl();
		SparkConf conf = new SparkConf();
		conf.setMaster("local[*]");
		conf.setAppName("Spark shell");
		conf.set("spark.driver.host", "127.0.0.1");
		JavaSparkContext con = new JavaSparkContext(conf);
		JavaStreamingContext context = new JavaStreamingContext(con, new Duration(5000L));
		Map<String, Object> props = new HashMap<>();
	    props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");        
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			
		Collection<String> topics = Arrays.asList("prueba");

		final JavaInputDStream<ConsumerRecord<String, String>> stream =
			KafkaUtils.createDirectStream(
							  context,
					    LocationStrategies.PreferConsistent(),
					    ConsumerStrategies.<String, String>Subscribe(topics, props)
					  );
				
			JavaPairDStream<String, String> lines = stream.mapToPair((ConsumerRecord<String, String> record) ->
				{ return new Tuple2<>(record.key(), record.value());});
							
			lines.foreachRDD(rdd -> {
	            rdd.values().foreachPartition(p -> {
	                while (p.hasNext()) {
	                	String value = p.next(); 
	                	System.out.println("VALUE ->" + value);	 
	                	mongo.save(value);
	                   
	                }
	            });
	        });
		context.start();
		context.awaitTermination();
	}
}