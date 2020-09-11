package com.beignet.rabbitmq.service;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchange_name = "QQNews";
        String routingkey = "hola";
        String queueName = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(exchange_name,"direct",true);
        channel.queueBind(queueName,exchange_name,routingkey);
        while (true){
            String consumerTag = "";
            boolean autoAck = false;
            channel.basicConsume(queueName,autoAck,consumerTag,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键：" + routingKey);
                    System.out.println("消费的内容类型：" + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    //确认消息
                    channel.basicAck(deliveryTag, false);
                    System.out.println("消费的消息体内容：");
                    String bodyStr = new String(body, "UTF-8");
                    System.out.println(bodyStr);
                }
            });
        }
    }
}
