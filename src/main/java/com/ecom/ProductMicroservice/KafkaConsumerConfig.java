package com.ecom.ProductMicroservice;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.ecom.ProductMicroservice.DTO.OrderUpdateDTO;
import com.ecom.ProductMicroservice.KafkaEvents.ProductsAddedEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

//	
//	    @Bean
//	    public ConsumerFactory<String, OrderUpdateDTO> consumerFactory() {
//	        Map<String, Object> consumerProps = new HashMap<>();
//	        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//	        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "product-order-update-group");
//	        consumerProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderUpdateDTO.class);
//	        return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), new JsonDeserializer<>(OrderUpdateDTO.class));
//	    }
//
//	    @Bean
//	    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderUpdateDTO>> kafkaListenerContainerFactory() {
//	        ConcurrentKafkaListenerContainerFactory<String, OrderUpdateDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
//	        factory.setConsumerFactory(consumerFactory());
//	        return factory;
//	    }
	
    @Bean
    public ConsumerFactory<String, ProductsAddedEvent> consumerFactory() 
    {
        JsonDeserializer<ProductsAddedEvent> deserializer = new JsonDeserializer<>(ProductsAddedEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "product-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductsAddedEvent> kafkaListenerContainerFactory() 
    {
        ConcurrentKafkaListenerContainerFactory<String, ProductsAddedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}