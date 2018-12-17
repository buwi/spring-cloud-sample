/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  org.springframework.cloud.stream.annotation.EnableBinding
 *  org.springframework.cloud.stream.annotation.Output
 *  org.springframework.cloud.stream.annotation.StreamListener
 *  org.springframework.cloud.stream.messaging.Processor
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ComponentScan
 *  org.springframework.integration.annotation.InboundChannelAdapter
 *  org.springframework.integration.annotation.Poller
 *  org.springframework.integration.core.MessageSource
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.handler.annotation.SendTo
 *  org.springframework.messaging.support.MessageBuilder
 */
package com.spring.cloud.sample.config.spring;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

import com.spring.cloud.sample.config.spring.AppConfig.TestOutput;

@EnableBinding(value = {TestOutput.class})
@ComponentScan(basePackages = {"com.spring.cloud.sample"})
public class AppConfig {

	private AtomicInteger index = new AtomicInteger();

	@Bean
	@InboundChannelAdapter(value = TestOutput.TEST_OUTPUT, poller = {@Poller(fixedDelay = "10000", maxMessagesPerPoll = "1")})
	public MessageSource<String> notificationMessageSource() {
		return () -> {
			String payload = this.generatePayload();
			System.out.println("Generated message: " + payload);
			return MessageBuilder.withPayload(payload).setHeader("routingKey", "test").setHeader("test", "Y").build();
		};
	}

	private String generatePayload() {
		return "test : " + this.index.incrementAndGet();
	}

	@StreamListener(value = "input")
	@SendTo(value = {"output"})
	public String transform(String payload) {
		System.out.println("Sending message: " + payload + " to RabbitMQ");
		return "Converted:" + payload;
	}

	public static interface TestOutput extends Processor {

		public static final String TEST_OUTPUT = "test";

		@Output(value = "test")
		public MessageChannel testOutput();
	}

}
