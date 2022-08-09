package com.comp6591.lib.database;

import com.comp6591.lib.Clause;

import java.util.*;

public class ExtensionalDB {
    private Map<String, List<Clause>> edb;
    private Set<String> facts;

    public ExtensionalDB() {
        edb = new HashMap<>();
        facts = new HashSet<>();
    }

    public boolean isExisted(String name) {return this.edb.keySet().contains(name); }

    public void addFact(Clause fact) {
        if (fact == null) return;
        if (edb.containsKey(fact.getName())) {
            if (edb.get(fact.getName()).add(fact)) {
                for (Clause c: fact.getArgs()) {
                    facts.add(c.toString());
                }
            }
        } else {
            List<Clause> factList = new ArrayList<>();
            edb.put(fact.getName(), factList);
            for (Clause c: fact.getArgs()) {
                facts.add(c.toString());
            }
        }
    }

    public boolean isEmpty() { return this.edb.isEmpty(); }
    public void printHU() {
        System.out.print("HU: ");
        for (String fact: facts) {
            System.out.print(fact + ", ");
        }
        System.out.println();
    }

    public void printSchema() {
        System.out.println("Schema: ");
        for (String schema : edb.keySet()) {
            System.out.print(schema + ", ");
        }
        System.out.println();
    }

    public void printEdb(){
        System.out.println("Edb");
    }

    public Set<String> getFacts() {
        return facts;
    }

    public void empty() {
        edb.clear();
        facts.clear();
    }
}
