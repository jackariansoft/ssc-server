package mude.srl.ssc.messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	public static final String AGGIORNAMENTO_WEBSOCKET_ENDPOINT = "/aggiornamento";
	public static final String INFO_WEBSOCKET_ENDPOINT = "/info";

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		
		config.setApplicationDestinationPrefixes("/prenotazioni");
		config.enableSimpleBroker(AGGIORNAMENTO_WEBSOCKET_ENDPOINT, INFO_WEBSOCKET_ENDPOINT);
		

	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/prenostazione-risorse").setAllowedOrigins("*").withSockJS();

	}
}
