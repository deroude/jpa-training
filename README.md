# JPA Training

Hey, guys!

This is testbed to get a feel of several rather common JPA tricks and pitfalls.

To get it running, just run `docker-compose up`, then run the Spring Boot application.

Note that, in the `application.yaml` config, the sql logging is turned on, to allow us to see what Hibernate generates from our queries.

For most cases, there's a `/bad` and a `/good` endpoint to try. Be sure to follow the logs and see what happens.

# A few forward guidelines

## Design for big data

Always assume the data in a DB is much larger than your RAM, even if it currently is not.

If the requirement is to see ALL invoices in a table, in the UI, simply fetching ALL invoices is not a solution.
Even if it works now, assume that it will either be a very long operation, or it will throw an Out Of Memory exception.

The immediate solution is to use __pagination__. It is built into the Spring Data JPA.

This does not, however, work all the time. A requirement may be, for example, to update all items in a table.

## Data operations (filtering, sorting, modifying) are best done by the DB, not by the client

A common anti-pattern is `query All - process - save back`, or `query All - filter - sort`. 

The only situation where this is a valid pattern is when the `process` part depends on a complex (non-serializable) application state, or on a dependent library.
In general, when the processing cannot be done by the DB.

Otherwise, it has several massive disadvantages:

- in the simplest case, it makes two round trips to the DB (query + save)
- ... but the size of data transferred is relatively big
- ... and, unless batched, it is all loaded in RAM
- processing / filtering is done on a collection with a generic implementation
- ... as opposed to a specialized data structure with advanced capabilities engineered specifically for those purposes

## Always filter based on indexed columns (add indexes where necessary)

An application is likely to have a rather limited amount of queries for its operations.
Make sure the filter conditions are backed by indexes.

Feel free to run your queries on the DB with an `explain` clause, or whatever that DB uses for query analysis.
Non-indexed conditions will show with a much higher cost than the others and will be described as `full table scan` or similar.

## Round trips to the DB are expensive (often more so than the queries themselves)

The goal is to minimize the amount of DB requests necessary to perform a certain task.

### N+1 problem

In the example, you've got a relationship between `Invoice` and `InvoiceItem`.

This is described as a `@OneToMany` field on the `Invoice` side and a `@ManyToOne` field on the `InvoiceItem` side -- which means this is a bidirectional relationship.

The expectation is to be able to retrieve a collection of `Invoice` with all its `InvoiceItem` sub-entities.

Using the default `findAll` method in the `JPARepository` interface, this generates __N+1 queries__ with N being the number of `Invoice` entities returned by the first query.

This is horribly inefficient, when we think that you can retrieve that information easily with just one query.

There are two solutions to that:

- `JOIN FETCH` -- [doc here](https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/fetch-join.html)
- `@NamedEntityGraph` -- [doc here](https://thoughts-on-java.org/jpa-21-entity-graph-part-1-named-entity/)

Unfortunately, none of the two solutions work with pagination, if you're using Hibernate.

This leaves you with four solutions:

- Accept the N+1 problem, because you'll only fetch one page
- Make the `items` field lazy, by setting `fetch = FetchType.LAZY` to the `@OneToMany` annotation (this is the default) -- but lose the sub-collection in the returned object
- Make two queries and stitch them together:
  - one query to fetch the `Invoice` result
  - the second query to fetch the `InvoiceItem` objects with a filter condition to have the FK within the ids returned by the first query
- Make a single query with `join` -- but you'll need to reconstruct the entities manually

### The not-so-lazy field

We are often marking an Entity field with `Lazy` fetch and expecting it will never get 
queried.

However, `Lazy` just means it will not get queried __immediately__. 

If you call the getter for that field, it will get queried. That's what serialization 
does: it calls the getter fields.

To prevent it from getting queried, you can use `@JsonIgnore` or the `@JsonView` 
stack on it.

### The stack overflow

Having the bidirectional `Invoice`-`InvoiceItem` relationship, without any additional intervention, will lead to a stack overflow when serving a web request (i.e. performing Jackson serialization).

Why is that? it's quite simple actually: an `Invoice` has a collection `InvoiceItem`; each `InvoiceItem` has its parent `Invoice`, etc.

There are multiple solutions to that -- [doc here](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)

## Updates in Java

Most databases have the `UPDATE` command available. 
What it does is it applies a transformation to an arbitrary number of rows.

There are 2 major advantages to using `UPDATE` instead of performing 
data updates on the client (Java in this case):

- when updating in the client, it needs to fetch the data, deserialize to apply the updates, 
serialize and send the updated rows back then (optionally) retrieve the updated rows -- 
that's 2 or 3 round trips to the DB, of which 1 or 2 are __large__; 
when updating in the DB, there's just one small modifying query and (optionally) 
one large fetch to retrieve the resulting rows. 
- the DB is __far__ more efficient at performing update queries than your client (i.e. Java)

## Use the index, Luke

## One direction

In a nutshell, unless there's a really strong reason to make a relationship bidirectional,
they shouldn't be. The direction of a relationship is commonly dictated by the presentation --
do I want to display `Vendor` on every `Invoice` or the `Invoice` list on every `Vendor`? 
The answer is pretty clear :)