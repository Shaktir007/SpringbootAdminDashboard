package com.app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@PostConstruct
	public void init() {
		logger.debug("SecurityConfig has been initialized.");
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.debug("Configuring security settings started...");

		http.authorizeHttpRequests(authz -> authz.requestMatchers("/static/**", "/js/**", "/images/**", "/css/**","/perform-logout")
				.permitAll().requestMatchers("/home").authenticated().requestMatchers("/api/**").authenticated()
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").permitAll()
						.successHandler(new SimpleUrlAuthenticationSuccessHandler("/home"))
						.failureHandler(new SimpleUrlAuthenticationFailureHandler()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.maximumSessions(1).expiredUrl("/login?sessionExpired=true"))
				.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/403"));

		logger.debug("Configuring security settings completed...");

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				User.withUsername("user").password(passwordEncoder().encode("user")).roles("USER").build(),
				User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN").build());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
