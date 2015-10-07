package org.kj6682.samarcanda.loan;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


/**
 * Created by luigi on 30/09/15.
 * <p>
 * **loan** - a loan relates a user to an item and has a beginning and end date. To keep things simple we do not care about bookings or extension of the loans
 */

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date begin;
    private Date end;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Item> items;

    public Loan() {
    }

    public Loan(Date begin, Date end) {
        this.begin = begin;
        this.end = end;
    }

    public Loan(User user, Set<Item> items, Date end, Date begin) {
        this.user = user;
        this.items = items;
        this.end = end;
        this.begin = begin;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}//:)
