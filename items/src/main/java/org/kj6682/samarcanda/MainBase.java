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
@Profile("production")
public class MainBase {

    private final static Logger logger = LoggerFactory.getLogger(MainBase.class);

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
}
