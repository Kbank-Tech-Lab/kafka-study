package org.channel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
//@EnableJpaRepositories(basePackages = {"org.channel.repository"})
//@EntityScan(basePackages = {"org.channel.dto"} )
@SpringBootApplication
public class SpringChannelApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringChannelApplication.class, args);
    }
}