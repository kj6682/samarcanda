package org.kj6682.samarcanda.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by luigi on 30/09/15.
 */
@Component
public interface ItemRepository extends CrudRepository<Item, Long> {
}
