package org.kj6682.samarcanda;

import org.kj6682.samarcanda.item.Item;
import org.kj6682.samarcanda.item.ItemRepository;
import org.kj6682.samarcanda.org.kj6682.samarcanda.location.Location;
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

    @Profile("demo")
    @Bean
    public CommandLineRunner demo(ItemRepository itemRepository) {

        Iterable<Item> items = itemRepository.findAll();

        return (args) -> {

            Arrays.asList("1,2,3,4,5,6,7,8".split(",")).
                    forEach(l -> {
                        Item item = new Item(l,  "", new Location());
                        itemRepository.save(item);
                    });


        };
    }
}//:)
