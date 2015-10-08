package org.kj6682.samarcanda;

import org.kj6682.samarcanda.item.Item;
import org.kj6682.samarcanda.item.ItemRepository;
import org.kj6682.samarcanda.item.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

/**
 * Created by luigi on 08/10/15.
 */
@Configuration
@Profile("test")
public class TestBase {

    private final static Logger logger = LoggerFactory.getLogger(TestBase.class);

    @Bean
    public CommandLineRunner demo(ItemRepository itemRepository) {
        return (args) -> {
            Arrays.asList("201,301,401,501,701".split(",")).
                    forEach(l -> {
                        Item item = new Item(l, "", new Location());
                        itemRepository.save(item);
                    });


        };
    }
}
