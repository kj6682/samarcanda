package org.kj6682.samarcanda.loan;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by luigi on 30/09/15.
 */
@Component(value = "LoanItemRepository")
public interface ItemRepository extends CrudRepository<Item, Long> {
}
