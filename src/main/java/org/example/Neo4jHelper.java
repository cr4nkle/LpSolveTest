package org.example;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;

public class Neo4jHelper implements AutoCloseable {
    private final Driver driver;

    public Neo4jHelper(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws RuntimeException {
        driver.close();
    }

    public void read() {
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "rootroot"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (p:Person) RETURN p");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println(record.get("p").asNode().get("name").asString());
                }
            }
        }
    }

    public void write(){
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "rootroot"))) {
            try (Session session = driver.session()) {
                // Запись данных
                session.writeTransaction(tx -> {
                    tx.run("CREATE (a:Person {name: 'Alex Dereza'})");
                    return null;
                });
            }
        }
    }
}
