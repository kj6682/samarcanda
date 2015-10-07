package org.kj6682.samarcanda.loan;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by luigi on 30/09/15.
 *
 * a user is a person with a name, a role (admin, customer) and an address. A user can have several loans
 *
 *
 */
@Entity(name = "LoanUser")
public  class User {

    @Id
    private Long id;

    public User() {
    }

    public User(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}//:)
