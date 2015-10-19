package org.kj6682.samarcanda.org.kj6682.samarcanda.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by luigi on 30/09/15.
 *
 * **location** - is a place identified by co-ordonates (site, store, shelf)
 *
 */

@Entity
public class Location {

    @Id
    @GeneratedValue
    private String id;
    private String site;
    private String store;
    private String shelf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("id=").append(id);
        sb.append(", site='").append(site).append('\'');
        sb.append(", store='").append(store).append('\'');
        sb.append(", shelf='").append(shelf).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
