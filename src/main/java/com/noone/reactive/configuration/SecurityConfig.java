/**
 * 
 */
package com.noone.reactive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

/**
 * @author sunil.yadav
 *
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurerComposite() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }
    
    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(
      ServerHttpSecurity http) {
        return http.authorizeExchange()
          .anyExchange().permitAll()
          .and()
          .csrf().disable()
          .build();
    }
	
	 /*@Override
     public CorsConfiguration getCorsConfiguration(final HttpServletRequest request) {
         return new CorsConfiguration().applyPermitDefaultValues();
     }*/
}
