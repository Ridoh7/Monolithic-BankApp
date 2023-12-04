//package com.OptimistBank.BankApp.SecurityConfig;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@EnableWebSecurity
//public class BankSecConfig {
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails firstUser= User.withUsername("ridoh")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//        UserDetails secondUser=User.withUsername("lukman")
//                .password(passwordEncoder().encode("12345"))
//                .roles("USER")
//                .build();
//        //save objects in Internal memory
//        return new InMemoryUserDetailsManager(firstUser,secondUser);
//    }
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(authorize->
////                        authorize.requestMatchers("/api/users/**").permitAll()
////                                .requestMatchers("/api/users/register").permitAll()
////                                .anyRequest().permitAll());
////        return http.build();
//////
////    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
