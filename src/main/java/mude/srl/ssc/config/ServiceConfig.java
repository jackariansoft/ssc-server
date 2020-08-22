/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.config;

import mude.srl.ssc.interceptor.HeartBitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/public/**")
                        .addResourceLocations("/public/");
        }

   

}
