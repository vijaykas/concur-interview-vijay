package com.concur.interview;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ItemsApplication {

    private static Log logger = LogFactory.getLog(ItemsApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ItemsApplication.class, args);
    }

    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {

            public void contextInitialized(
                    ServletContextEvent servletContextEvent) {
                logger.info("ServletContext initialized");
            }

            public void contextDestroyed(
                    ServletContextEvent servletContextEvent) {
                logger.info("ServletContext destroyed");
            }
        };
    }
}
