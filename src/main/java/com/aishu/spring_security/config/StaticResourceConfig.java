//package com.aishu.spring_security.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class StaticResourceConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // serve /assets/** from src/main/resources/static.assets/
//        registry.addResourceHandler("/assets/**")
//                .addResourceLocations("classpath:/static.assets/");
//
//        // or serve root files like /favicon.png from an external folder
//        registry.addResourceHandler("/favicon.png")
//                .addResourceLocations("file:/Users/manikandan/projects/myapp/static.assets/favicon.png");
//
////        registry.addResourceHandler("/css/**")
////                .addResourceLocations("classpath:/static.assets/css/");
////        registry.addResourceHandler("/js/**")
////                .addResourceLocations("classpath:/static.assets/js/");
//
//    }
//}
