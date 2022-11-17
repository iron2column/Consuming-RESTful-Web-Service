package top.lessmore.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author ChenMingYang
 * @date 2022-11-17 11:22
 */
@Component
public class Customer {
    @Bean
    public RestTemplate otherRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner otherRun(RestTemplate restTemplate) {
        return args -> {
            Quote quote = restTemplate.getForObject("http://localhost:8081/greeting", Quote.class);
            if (quote != null) {
                System.out.println(quote);
            } else {
                System.out.println("Not Found");
            }
        };
    }
}
