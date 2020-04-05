/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.config.security;

import it.ariannamondo.mag.config.JwtAuthenticationEntryPoint;
import it.ariannamondo.mag.config.JwtRequestFilter;
import it.ariannamondo.mag.config.endpoint.ServiceEndpoint;
import it.ariannamondo.mag.services.user.services.UserService;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author jackarian
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements AccessDecisionManager {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// configure AuthenticationManager so that it knows from where to load
// user for matching credentials
// Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new AbstractPasswordEncoder() {
            @Override
            protected byte[] encode(CharSequence cs, byte[] bytes) {
                Logger.getLogger(SecurityConfig.class.getName()).
                        log(Level.INFO, "Encode 1");
                Logger.getLogger(SecurityConfig.class.getName()).
                        log(Level.INFO, "CS {0}", cs.toString());
                Logger.getLogger(SecurityConfig.class.getName()).
                        log(Level.INFO, "Bytes: {0}", new String(bytes, Charset.forName("UTF-8")));
                String encrypt = null;
                encrypt = encode(cs);

                return encrypt.getBytes();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                try {
                    //                Logger.getLogger(SecurityConfig.class.getName())
//                        .log(Level.INFO, "Try matching {0} and {1}",
//                                new Object[]{rawPassword,encodedPassword});

                    String encodeRaw = encode(rawPassword);
//boolean match = encodeRaw.equals(encodedPassword);
//                if(match){
//                    Logger.getLogger(SecurityConfig.class.getName()).
//                        log(Level.INFO, "They match");
//                }
                    return MessageDigest.isEqual(encodeRaw.getBytes("UTF-8"), encodedPassword.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
            }

            @Override
            public String encode(CharSequence password) {
//                Logger.getLogger(SecurityConfig.class.getName()).
//                        log(Level.INFO, "Encode 2");
//                Logger.getLogger(SecurityConfig.class.getName()).
//                        log(Level.INFO, "Password: {0}", password);

                StringBuilder hexString = new StringBuilder();
                String encodedResult = null;
                try {
                    MessageDigest dig = MessageDigest.getInstance("MD5");
                    dig.update(password.toString().getBytes("UTF-8"), 0, password.length());
                    byte[] digest = dig.digest();

                    for (int i = 0; i < digest.length; i++) {
                        String hex = Integer.toHexString(0xFF & digest[i]);
                        if (hex.length() == 1) {
                            hexString.append("0");
                        }
                        hexString.append(hex);

                    }
                    encodedResult = hexString.toString();
                    //Logger.getLogger(SecurityConfig.class.getName()).log(Level.INFO, " Encoded  string: {0}",encodedResult);
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(SecurityConfig.class.getName()).log(Level.SEVERE, " Erro encoding string", ex);
                }
                return encodedResult;
            }
        };
        //return new BCryptPasswordEncoder();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().
                antMatchers(ServiceEndpoint.LOGIN).permitAll().
                antMatchers(ServiceEndpoint.REGISTER).permitAll().
                antMatchers("/swagger-ui.html/**").permitAll()
               .antMatchers("/v2/**").permitAll()
               .antMatchers("/csrf/**").permitAll()
               .antMatchers("/webjars/**").permitAll()
               .antMatchers("/swagger-resources/**").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().accessDecisionManager(this).and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
         if(authentication!=null){
             
         }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        if(attribute!=null){
            
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
