package org.kj6682.samarcanda.item;

import javax.persistence.*;

/**
 * Created by luigi on 30/09/15.
 *
 * items are objects such as books, dvds, cds or whatever can be stored in a *location*.
 * An Item can have only one location and can possibly be lent only once to a person
 *
 */

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String by;

    @ManyToOne(cascade= CascadeType.ALL)
    private Location location;

    public Item() {
    }

    public Item(String title, String by, Location location ) {

        this.by = by;
        this.title = title;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("by=").append(by);
        sb.append(", id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }
}//:)
