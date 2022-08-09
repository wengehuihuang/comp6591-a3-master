package com.comp6591.lib;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*

        Scanner scan = new Scanner(System.in);
        System.out.print("database path: > ");
        System.out.println(System.getProperty("java.library.path"));
        //String databasePath = scan.nextLine();
        //System.out.println(databasePath);
        try {
            FileInputStream in = new FileInputStream("a.pl");
            Arrays.stream(ProgramParser.parseKB(in)).forEach(clause -> {
                System.out.println("\nClause: " + clause);
                System.out.println("Type: " + clause.getType());
                switch (clause.getType()) {
                    case RULE:
                        System.out.println("Head: " + clause.getHead());
                        System.out.println("Goals: " +
                                Arrays.toString(clause.getGoals()));
                        System.out.println("Head variable(s): " +
                                Arrays.toString(clause.getHead().getVariables()));
                        System.out.println("Rule variable(s): " +
                                Arrays.toString(clause.getVariables()));
                        System.out.println("Goal(1) Instantiate X=2, Y=1: " +
                                clause.getGoals()[0].substitute(
                                        MultiValueMap.parse("X=2&Y=1", true)));
                        break;
                    case PROC:
                        System.out.println("Proc Name: " + clause.getName());
                        System.out.println("Argument: " +
                                Arrays.toString(clause.getArgs()));
                        break;
                    default:
                        System.out.println("???");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        Engine eg = new Engine();
        Scanner l_scanner = new Scanner(System.in);

        System.out.println("Import rules");
        //String filePath = "./rules_1.pl";
        String filePath = l_scanner.nextLine();
        try{
            eg.readFile(filePath);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Import facts.");
        //filePath = "./facts_1.pl";
        filePath = l_scanner.nextLine();
        if(filePath == ""){
            System.out.println("Empty database.");
        }
        else{
            try{
                eg.readFile(filePath);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        eg.setHBase();

        System.out.println("Change the rules.");
        filePath = l_scanner.nextLine();
        eg.emptyIDB();
        try {
            eg.readFile(filePath);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Change the facts.");
        filePath = l_scanner.nextLine();
        eg.emptyEDB();
        try {
            eg.readFile(filePath);
        }catch(IOException e){
            e.printStackTrace();

        }
        eg.setHBase();

        System.out.println("Show the output.");
        eg.output();
    }
}