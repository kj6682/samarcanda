package org.kj6682.samarcanda.loan;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by luigi on 30/09/15.
 *
 * items are objects such as books, dvds, cds or whatever can be stored in a *location*.
 * An Item can have only one location and can possibly be lent only once to a person
 *
 */

@Entity(name = "LoanItem")
public  class Item {

    @Id
    private Long id;

    public Item() {
    }

    public Item(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}//:)