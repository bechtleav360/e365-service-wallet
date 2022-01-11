package com.bechtle.eagl.UserMappingsService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value("${security.api-key}")
    String key;

    private static final String API_KEY_HEADER = "X-API-KEY";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveAuthenticationManager authenticationManager,
                                                         ServerAuthenticationConverter authenticationConverter) {
        final AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

        return http
                .authorizeExchange(spec -> spec.pathMatchers("/api/**").authenticated()
                        .anyExchange().permitAll())
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .build();

    }

    @Bean
    public ReactiveAuthenticationManager buildAuthenticationManager() {
        return authentication -> {
            if (authentication == null) return Mono.empty();

            if (authentication.getPrincipal() != null
                    && authentication.getCredentials().toString().equalsIgnoreCase(this.key)
            ) {
                authentication.setAuthenticated(true);
            }

            return Mono.just(authentication);
        };
    }

    @Bean
    ServerAuthenticationConverter buildAuthenticationConverter() {
        return exchange -> {
            List<String> headers = exchange.getRequest().getHeaders().get(API_KEY_HEADER);
            if (headers == null) return Mono.empty();
            if (headers.size() == 0) return Mono.empty();

            return Mono.just(new ApiKeyAuthentication(headers.get(0)));
        };
    }



    private static class ApiKeyAuthentication extends AbstractAuthenticationToken {

        private final String apiKey;

        public ApiKeyAuthentication(String apiKey) {
            super(null);
            this.apiKey = apiKey;
        }

        @Override
        public Object getCredentials() {
            return this.apiKey;
        }

        @Override
        public Object getPrincipal() {
            return "api_user";
        }
    }

}
