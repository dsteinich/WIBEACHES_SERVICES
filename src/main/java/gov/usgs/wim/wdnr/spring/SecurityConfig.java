package gov.usgs.wim.wdnr.spring;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

//@EnableWebSecurity
public class SecurityConfig {// extends WebSecurityConfigurerAdapter {

//	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
					.anyRequest().permitAll()
			.and()
				.requiresChannel().anyRequest().requiresSecure()
			.and()
				.anonymous()
		;
	}

}