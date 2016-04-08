package com.lang2am.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

@SpringBootApplication(scanBasePackages="com.lang2am")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

	@Bean
    public ViewResolver getViewResolver() {
    	HandlebarsViewResolver handlebarsViewResolver = new HandlebarsViewResolver();
//        handlebarsViewResolver.setViewNames("handlebars:*");
        handlebarsViewResolver.setPrefix("/WEB-INF/handlebars/");
        handlebarsViewResolver.setSuffix(".hbs");
        handlebarsViewResolver.setOrder(1);
        handlebarsViewResolver.setRequestContextAttribute("rc");
        handlebarsViewResolver.setCache(false);
        return handlebarsViewResolver;
    }
}