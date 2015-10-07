package org.kj6682.samarcanda.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

/**
 * Created by luigi on 30/09/15.
 */
@RestController
public class ItemController {

    final static Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Inject
    private ItemRepository itemRepository;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Item>> getAllItems(){
        Iterable<Item> items = itemRepository.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity<?> createItem(@RequestBody Item item){

        item = itemRepository.save(item);

        //Set the location header for the newly created item
        HttpHeaders responseHeaders = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(item.getId())
                .toUri();
        responseHeaders.setLocation(uri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<?> getItem(@PathVariable Long itemId) {
        Item p = itemRepository.findOne(itemId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(value="/items/{itemId}", method=RequestMethod.PUT)
    public ResponseEntity<?> updateItem(@RequestBody Item item, @PathVariable Long itemId) {
        // Save the entity
        Item newItem = itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/items/{itemId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        itemRepository.delete(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}//:)
