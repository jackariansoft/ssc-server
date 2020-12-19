/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.config;

import mude.srl.ssc.interceptor.HeartBitInterceptor;

import java.awt.image.BufferedImage;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Jack
 */
@Configuration
@EnableWebMvc
public class ServiceConfig implements WebMvcConfigurer {

	@Autowired
	HeartBitInterceptor hearbit;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(hearbit);
	}
	
	@Override
    public void configureMessageConverters(
      List<HttpMessageConverter<?>> converters) {
    
		converters.add(createImageHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
        
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/public/**").addResourceLocations("/public/");
	}

	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}

}
