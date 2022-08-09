package com.comp6591.lib;

import com.comp6591.lib.database.ExtensionalDB;
import com.comp6591.lib.database.IntensionalDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Engine {
    private ExtensionalDB edb;
    private IntensionalDB idb;

    public Engine() {
        edb = new ExtensionalDB();
        idb = new IntensionalDB();
    }

    public void readFile(String filePath) throws IOException {
        Arrays.stream(ProgramParser.parseKB(new FileInputStream(filePath))).forEach(clause -> {
            System.out.println("\nClause: " + clause);
            System.out.println("Type: " + clause.getType());
            switch (clause.getType()) {
                case RULE:
                    System.out.println("Head: " + clause.getHead());
                    System.out.println("Goals: " + Arrays.toString(clause.getGoals()));
                    System.out.println("Head variables: " + Arrays.toString(clause.getHead().getVariables()));
                    System.out.println("Rule variables: " + clause.getVariables());
                    idb.addRule(clause);
                    break;

                case PROC:
                    System.out.println("Proc Name: " + clause.getName());
                    System.out.println("Arguments: " + Arrays.toString(clause.getArgs()));
                    edb.addFact(clause);
                    break;

                default:
                    System.out.println("???");
            }
        });
    }

    public void emptyIDB() { idb.empty(); }
    public void emptyEDB() { edb.empty(); }
    public boolean readQuery(String query) {
        if (query == null || query.isEmpty()) {
            System.out.println("Invalid query");
        } else{
                Clause clause = Clause.parse(query);
                return false;
        }
        return true;
    }

    public void setHBase() {
        if (!idb.isEmpty() && !edb.isEmpty()) {
            idb.setHBbase(edb.getFacts());
        }
    }
    public void output() {
        System.out.println("the Database: ");
        edb.printEdb();
        idb.printIdb();

        System.out.println("the Schema:");
        edb.printSchema();
        idb.printIdbSchema();

        System.out.println("the HU:");
        edb.printHU();

        System.out.println("the Schema:");
        edb.printSchema();
        idb.printIdbSchema();



    }
}
