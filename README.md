# SamaRcanda

### *a very boring RESTful application* [1.0]
the day has come for a tool to manage a generic inventory because I am tired of not knowing where my books or videos are in this moment
* each item is the representation for an object (book, movie, stuff..)
* to keep things simple, by now each item has a name, an author and a location
* items are managed in a repository
* two kind of users can ask for services
  1. **admin** - who can do usual CRUD plus move and get current location of items
  2. **customer** - who can read, borrow, return and get current location of items
* services must be versioned
* security is done via Spring-Security
* rest is done via Spring-Boot
* clients are out of scope

the design of this stupid RESTful application involves the following steps :
1. <b>resources identification
2. <b>resource representation
3. <b>endpoint identification
4. <b>verb and action identification

## Resource Identification
It is time to define *nouns* from our analysis.

* **user** - a user is a person with a name, a role (admin, customer) and an address. A user can have several loans

* **users** - collection of users

* **item** - items are objects such as books, dvds, cds or whatever can be stored in a *location*. An Item can have only one location and can possibly be lent only once to a person

* **items** - collection of items

* **location** - is a place identified by co-ordonates (site, store, shelf)

* **locations** - a list of locations

* **loan** - a loan relates a user to an item and has a beginning and end date. To keep things simple we do not care about bookings or extension of the loans

* **loans** - a list of loans

## Resource Representation  
We deal with JSON representation only   

**user**  
```json
{  
  "id":123,  
  "name":"Ciccio Caluppo",  
  "role":"admin",  
  "address":"via dei Rospi 245, Borgo San Silvestro, 12344 Pesaro"  
}  
```
**users**  
```json
[  
{"id":123, "name":"Ciccio Caluppo", "role":"admin", "address":"via dei Rospi 245, Borgo San Silvestro, 12344 Pesaro" },  
{"id":124, "name":"Michele Lanocella", "role":"customer", "address":"via dei Gattamelata 25, 12444 Pescara"},  
{"id":125, "name":"Gino Formica", "role":"guest", "address":"viale dei Mille 54, 14344 Rovigo"}
]
```
**item**
```json
{  
  "id":5667,  
  "title":"Algorithms, Fourth Edition",  
  "by": ["Robert Sedgewick", "Kevin Wayne"],  
  "location":{  
    "id":12,  
    "site":"stanza rossa",
    "store":"armadio A",
    "shelf":"scaffale 3"        
  }  
}
```
**items**
```json
[
{ "id":5667, "title":"Algorithms, Fourth Edition", "by": ["Robert Sedgewick", "Kevin Wayne"], "location":{  "id":12, "site":"stanza rossa", "store":"armadio A", "shelf":"scaffale 3"} },
{ "id":5668, "title":"Serpendillando", "by": ["Guido Lavolpe"], "location":{ "id":12, "site":"stanza rossa", "store":"armadio A", "shelf":"scaffale 2"} }  
]
```
**location**
```json
{
  "id":12,
  "site":"stanza rossa",
  "store":"armadio A",
  "shelf":"scaffale 2"    
}
```
**locations**
```json
[
{ "id":12, "site":"stanza rossa", "store":"armadio A", "shelf":"scaffale 2" },
{ "id":13, "site":"stanza rossa", "store":"armadio A", "shelf":"scaffale 3" }
]
```
**loan**
```json
{
  "id":123,
  "from":"20150807",
  "to":"",
  "user":{  
    "id":124,
    "name":"Michele Lanocella",  
    "role":"customer",  
    "address":"via dei Gattamelata 25, 12444 Pescara"  
  },
  "item":{
    "id":5667,
    "title":"Algorithms, Fourth Edition",
    "by": ["Robert Sedgewick", "Kevin Wayne"],
    "location":{
      "id":12,
      "site":"stanza rossa",
      "store":"armadio A",
      "shelf":"scaffale 3"    
    }
  }
}
```

This could be perfectly reasonable if we want to design the exhaustive entity called "loan". A lot of redundance is spread here: all the user's details can be omitted as well as the item's. When we go forward a more "microservice" solution, we could think of a service dedicated to one and only one entity. We delegate the details to the main entity and we avoid using Hibernate annotation to join the entities.  
Wheter it is better to have server redundancy, by running several fat jars, or one single server that hosts the old application, is the question. It depends on the degree of parallelism that we want to achieve. Nevertheless, separating the entities to be flexibly exported in a dedicated jar does not impact on the developers time.  

```json
{
  "id":123,
  "from":"20150807",
  "to":"",
  "user":{  
    "id":124,
  },
  "item":{
    "id":5667,
  }
}
```  
We prefere the latter representation as we can better modularise the code and packages.


**loans**
```json
[{"id":123,"from":"20150807","to":"","user":{"id":124}, "item":{"id":5667}}
,
{"id":123,"from":"20140807","to":"20150101","user":{"id":124}, "item":{"id":5644}}}  
]
```

## Endpoint Identification

REST resources are identified using URI endpoints. Well-designed REST APIs should have endpoints that are understandable, intuitive, and easy to use.
We design the endpoints for our service using best practices and conventions widely used in the industry.
1. **use a base URI for REST services**  
for the moment http://localhost:8080 will be perfect  

2. **name resource endpoints using plural nouns**    
it is easier to address entities with plural nouns as we can always get a collection. In case of a single entity we address the collection with an entity id.  
<pre>
http://localhost:8080/users/
http://localhost:8080/users/{userId}
http://localhost:8080/items/
http://localhost:8080/items/{itemId}
http://localhost:8080/locations/
http://localhost:8080/locations/{locationId}
http://localhost:8080/loans/
http://localhost:8080/loans/{loanId}
</pre>

3. ** use URI hierarchy to represent resources that are related to each other **  
we need to define how the resources are related to each other. In other terms we want to refine the queries to be sure, for example to get the list of loans of a user, or the location of a particular idem.
<pre>
http://localhost:8080/users/{userId}/loans/
http://localhost:8080/users/{userId}/loans/{loanId}
http://localhost:8080/locations/{locationId}/items/
</pre>

But if we do this way we prevent ourself to be scalable and we couple users and loans. prefer this:  
<pre>
http://localhost:8080/locations/{locationId}/items/
</pre>


4. ** use a specific URI for calculated values **  
it might be interesting to activate a calculated value for our resources, such as the number of items on a location or the absolute number of items
<pre>
http://localhost:8080/numberOfItems/
http://localhost:8080/numberOfItemsLent/
http://localhost:8080/numberOfLoans/
</pre>

In this section, we have identified the endpoints for the resources in our QuickPoll application. The next step is to identify the actions that are allowed on these resources, along with the expected responses.

## Action Identification  
Now that we defined the endpoints, we want to define the actions we can do on the entities through them.

<pre>user</pre>

| HTTP Method | Endpoint  | Input  | Success Response  | Error Response  | Description |
|---|---|---|---|---|---|
| GET     | /users  | Body:Empty  | Status:200 Body:User list  | Status:500  | Retrieves all available users  |
| POST    | /users  | Body:New User Data  | Status:201 Body:userId  | Status:500  | Creates a new User  |
| PUT     | /users  | NA  | NA  | Status:400  | Forbidden action  |
| DELETE  | /users  | NA  | NA  | Status:400  | Forbidden action  |  
| GET     | /users/{userId}  | Body:Empty  | Status:200 Body:User data  | Status:404 or 500  | Retrieves a user details  |
| POST    | /users/{userId}  | NA  | NA  | Status:400  | Forbidden action  |
| PUT     | /users/{userId}  | Body:user data with updates  | Status:200 Body:Empty  | Status:404 or 500  | Updates a user  |
| DELETE  | /users/{userId}  | Body:Empty  | Status:200  | Status:404 or 400  | Deletes a user  |  
| GET     | /users/{userId}/loans  | Body:Empty  | Status:200 Body:User loans list  | Status:500  | Retrieves the list of loans of a user  |
| POST    | /users/{userId}/loans  | Body:new loan data  | Status:201  | Status:500  | creates a new loan for the user  |
| PUT     | /users/{userId}/loans  | NA  | NA  | Status:400  | Forbidden action  |
| DELETE  | /users/{userId}/loans  | NA  | NA  | Status:400  | Forbidden action  |  
| GET     | /users/{userId}/loans/{loanId}  | Body:Empty  | Status:200 Body:User data  | Status:404 or 500  | Retrieves a user loan details  |
| POST    | /users/{userId}/loans/{loanId}  | NA  | NA  | Status:400  | Forbidden action  |
| PUT     | /users/{userId}/loans/{loanId}  | Body:user data with updates  | Status:200 Body:Empty  | Status:404 or 500  | Updates a user loan |
| DELETE  | /users/{userId}/loans/{loanId}  | Body:Empty  | Status:200  | Status:404 or 400  | Deletes a user loan  |  
|    |   |   |   |   |   ||  

Again, this could be fine if we aggregate all the entities in one single application.
This table shows that we really want to tie users with their loans.
If we need to scale horizontally the loan service, which is more probable to be update instead of the user detail service, we have to deploy on the same measure "users" and "loans", thus we introduce a certain level of coupling.
If, on the other hand, we go for an extreme micro service, and we keep only one entity per service, we increase in flexibility.
How small is small enough?
This is the real question that only experience, common sense and probing can answer.
For example, we feel that "items" and "location" will not be used separately (at least not in our current vision), so we will deal with them in a common service "inventory". As the store needs to be upgraded, we will eventually split the two entities.  


The reasonable table for "users" it becomes:  

| HTTP Method | Endpoint  | Input  | Success Response  | Error Response  | Description |
|---|---|---|---|---|---|
| GET     | /users  | Body:Empty  | Status:200 Body:User list  | Status:500  | Retrieves all available users  |
| POST    | /users  | Body:New User Data  | Status:201 Body:userId  | Status:500  | Creates a new User  |
| PUT     | /users  | NA  | NA  | Status:400  | Forbidden action  |
| DELETE  | /users  | NA  | NA  | Status:400  | Forbidden action  |  
| GET     | /users/{userId}  | Body:Empty  | Status:200 Body:User data  | Status:404 or 500  | Retrieves a user details  |
| POST    | /users/{userId}  | NA  | NA  | Status:400  | Forbidden action  |
| PUT     | /users/{userId}  | Body:user data with updates  | Status:200 Body:Empty  | Status:404 or 500  | Updates a user  |
| DELETE  | /users/{userId}  | Body:Empty  | Status:200  | Status:404 or 400  | Deletes a user  |    
|    |   |   |   |   |   ||  

This option is clearly simpler.


<pre>item</pre>

| HTTP Method | Endpoint  | Input  | Success Response  | Error Response  | Description |
|---|---|---|---|---|---|
| GET     | /items  | Body:Empty  | Status:200 Body:item list  | Status:500  | Retrieves all available items  |
| POST    | /items  | Body:New User Data  | Status:201 Body:itemId  | Status:500  | Creates a new item  |
| PUT     | /items  | NA  | NA  | Status:400  | Forbidden action  |
| DELETE  | /items  | NA  | NA  | Status:400  | Forbidden action  |  
| GET     | /items/{itemId}  | Body:Empty  | Status:200 Body:item data  | Status:404 or 500  | Retrieves an item details  |
| POST    | /items/{itemId}  | NA  | NA  | Status:400  | Forbidden action  |
| PUT     | /items/{itemId}  | Body:user data with updates  | Status:200 Body:Empty  | Status:404 or 500  | Updates an item  |
| DELETE  | /items/{itemId}  | Body:Empty  | Status:200  | Status:404 or 400  | Deletes an item  |  
|    |   |   |   |   |   ||  

<pre>loan</pre>

| HTTP Method | Endpoint  | Input  | Success Response  | Error Response  | Description |
|---|---|---|---|---|---|
| GET     | /loans  | Body:Empty  | Status:200 Body:loan list  | Status:500  | Retrieves all available loans  |
| POST    | /loans  | Body:New User Data  | Status:201 Body:loanId  | Status:500  | Creates a new loan  |
| PUT     | /loans  | NA  | NA  | Status:400  | Forbidden action  |
| DELETE  | /loans  | NA  | NA  | Status:400  | Forbidden action  |  
| GET     | /loans/{loanId}  | Body:Empty  | Status:200 Body:loan data  | Status:404 or 500  | Retrieves a loan details  |
| GET     | /loans/item/{itemId}  | Body:Empty  | Status:200 Body:loan data  | Status:404 or 500  | Retrieves a list of loans given an item |
| GET     | /loans/user/{userId}  | Body:Empty  | Status:200 Body:loan data  | Status:404 or 500  | Retrieves a list of loans given a user  |
| POST    | /loans/{loanId}  | NA  | NA  | Status:400  | Forbidden action  |
| PUT     | /loans/{loanId}  | Body:loan data with updates  | Status:200 Body:Empty  | Status:404 or 500  | Updates a loan  |
| DELETE  | /loan/{loanId}  | Body:Empty  | Status:200  | Status:404 or 400  | Deletes a loan  |  
|    |   |   |   |   |   ||  


*** TODO *** define the actions for the other entities : location  

## Model Implementation
Now that we defined the Resource, we can go straight to the core of the application.
We define the package that will contain the entities, the repositories and the controllers in
```
package org.kj6682.samarcanda.user;

package org.kj6682.samarcanda.item;

package org.kj6682.samarcanda.loan
```
All these packages will contains at leas one controller, one model class and one repository. The entity is isolated and the micro-service can be isolated and deployed in multiple instance, all sharing the same data layer.
*** TODO *** define the verbs in the controller
