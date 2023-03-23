package com.nagp.notificationservice.rabbit.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitConfiguration implements RabbitListenerConfigurer{

	@Value("${flight.queue.name}")
	private String flightqueueName;

	@Value("${flight.fanout.exchange}")
	private String flightfanoutExchange;

	@Value("${hotel.queue.name}")
	private String hotelqueueName;

	@Value("${hotel.fanout.exchange}")
	private String hotelfanoutExchange;

	@Bean
	Queue flightQueue() {
		return new Queue(flightqueueName, true);
	}

	@Bean
	FanoutExchange flightExchange() {
		return new FanoutExchange(flightfanoutExchange);
	}

	@Bean
	Binding flightbinding() {
		return BindingBuilder.bind(flightQueue()).to(flightExchange());
	}

	@Bean
	Queue hotelqueue() {
		return new Queue(hotelqueueName, true);
	}

	@Bean
	FanoutExchange hotelexchange() {
		return new FanoutExchange(hotelfanoutExchange);
	}


	@Bean
	Binding hotelbinding() {
		return BindingBuilder.bind(hotelqueue()).to(hotelexchange());
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
}
