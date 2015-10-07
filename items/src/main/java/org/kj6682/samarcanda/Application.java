package org.kj6682.samarcanda;

import org.kj6682.samarcanda.item.Item;
import org.kj6682.samarcanda.item.ItemRepository;
import org.kj6682.samarcanda.item.Location;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

/**
 * Created by luigi on 30/09/15.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner production(ItemRepository itemRepository) {
        return (args) -> {

            Arrays.asList("1,2,3,4,5".split(",")).
                    forEach(l -> {
                        itemRepository.save(new Item(Long.valueOf(l), "", "", new Location()));
                    });


        };
    }
    @Bean
    @Profile("test")
    public CommandLineRunner demo(ItemRepository itemRepository) {
        return (args) -> {

            Arrays.asList("10,20,30,40,50".split(",")).
                    forEach(l -> {
                        itemRepository.save(new Item(Long.valueOf(l), "", "", new Location()));
                    });


        };
    }

}//:)
