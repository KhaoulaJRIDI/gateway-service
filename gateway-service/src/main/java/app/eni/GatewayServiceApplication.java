package app.eni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication


public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
@Bean
	RouteLocator staticRoutes(RouteLocatorBuilder builder) {
	return builder.routes()
			.route(r -> r
					.path("/muslim/**")
					.filters(f->f
							.addRequestHeader("x-rapidapi-host","muslimsalat.p.rapidapi.com")
							.addRequestHeader("x-rapidapi-key","4a637e7336msh845e52b4b12ae89p18aa31jsn461321ec909a")
							.rewritePath("/muslimsalat/(?<segment>.*)","/${segment}")
							.circuitBreaker(c->c.setName("muslim").setFallbackUri("forward:/defaultMuslim"))
					)
			.uri("https://muslimsalat.p.rapidapi.com")
					)
       .build();
}
	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}

}
@RestController
class CircuitBreakerRestController {
	@GetMapping("/defaultMuslim")
	public Map<String, String> salat() {
		 Map<String, String> data = new HashMap<>();
		data.put("message", "Horraire Salat");
		data.put("Fadjer","05:30 AM");
		data.put("Dhor","12:30 AM");
		data.put("Asr","03:30 AM");
		data.put("Maghreb","06:15 PM");
		data.put("isha","08:00 PM");
return data;
}}