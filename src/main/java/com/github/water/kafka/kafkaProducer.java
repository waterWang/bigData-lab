package com.github.water.kafka;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * @Author : water
 * @Date : 2016年12月19日
 * @Desc : kafka producer
 * @version: V1.0
 */
public class kafkaProducer extends Thread {

	private String topic;

	public kafkaProducer(String topic) {
		super();
		this.topic = topic;
	}

	@Override
	public void run() {
		Producer<Integer, String> producer = createProducer();
		int i = 0;
		while (true) {
			producer.send(new KeyedMessage<Integer, String>(topic, "message: " + i++));
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private Producer<Integer, String> createProducer() {
		Properties properties = new Properties();

		properties.put("zookeeper.connect", "10.100.6.147:2181,10.100.6.176:2181,10.100.6.177:2181");// 声明zk
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", "10.100.6.147:9092,10.100.6.176:9092,10.100.6.177:9092");// 声明kafka broker
		return new Producer<Integer, String>(new ProducerConfig(properties));

	}

	public static void main(String[] args) {
		kafkaProducer testProducer = new kafkaProducer("test");// 使用kafka集群中创建好的主题 test
		testProducer.start();
	}

}
