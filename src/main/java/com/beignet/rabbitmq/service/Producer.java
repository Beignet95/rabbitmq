package com.beignet.rabbitmq.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchange_name = "QQNews";
        channel.exchangeDeclare(exchange_name,"direct",true);
        String routingkey = "hola";

        channel.basicPublish(exchange_name,routingkey,null,"ok".getBytes("UTF-8"));


        connection.close();
        factory.clone();
    }

}
