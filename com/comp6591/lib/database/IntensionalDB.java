package com.comp6591.lib.database;

import com.comp6591.lib.Clause;
import com.comp6591.lib.MultiValueMap;

import java.util.*;

public class IntensionalDB {
    private List<Clause> idb;
    private List<String> idbSchema;
    private Set<String> hbBases;
    private Set<Clause> groundRules;
    private Stack<String> va;
    private Stack<String[]> vaList;

    public IntensionalDB() {
        this.idb = new ArrayList<>();
        this.hbBases = new HashSet<>();
        this.groundRules = new HashSet<>();
        this.va = new Stack<>();
        this.vaList = new Stack<>();
    }

    public void printIdb() {
        for (Clause rules: idb) {
            System.out.println(rules.toString());
        }
    }

    public void printIdbSchema() {
        System.out.print("Schema: ");
        for (String sc: idbSchema) {
            System.out.print(sc + ", ");
        }
        System.out.println();
    }

    public void addRule(Clause rule) {
        idb.add(rule);
        idbSchema.add(rule.getHead().getName());
    }

    public void empty() {
        idb.clear();
        idbSchema.clear();
        groundRules.clear();
        hbBases.clear();
    }

    public boolean isEmpty() {
        return this.idb.isEmpty();
    }

    public Set<String> setHBbase(Set<String> HU) {
        String[] HU_string = HU.toArray(String[]::new);
        for (Clause c: idb) {
            Set<String> variables = new HashSet<>();
            for (String str: c.getVariables()) {
                variables.add(str);
            }
            if (variables.isEmpty()) continue;
            String[] variablesStr = variables.toArray(String[]::new);
            va.clear();
            vaList.clear();
            dfs(HU_string, variables.size(), 0, 0);
            for (String[] vai: vaList) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < variablesStr.length; i++) {
                    stringBuilder.append(variablesStr[i]);
                    stringBuilder.append(",");
                    vai[i] = vai[i].replace("'", "");
                    stringBuilder.append(vai[i]);
                    stringBuilder.append("|");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                StringBuilder rules = new StringBuilder();
                rules.append(c.getHead().substitute(MultiValueMap.parse(stringBuilder.toString(), true)) + ":-");
                hbBases.add(c.getHead().substitute(MultiValueMap.parse(stringBuilder.toString(), true)));
                for (Clause goal : c.getGoals()) {
                    rules.append(goal.substitute(MultiValueMap.parse(stringBuilder.toString(), true)) + ",");
                    hbBases.add(goal.substitute(MultiValueMap.parse(stringBuilder.toString(), true)));
                }
                rules.deleteCharAt(rules.length() - 1);
                rules.append(".");
                groundRules.add(Clause.parse(rules.toString()));
            }
        }
        return this.hbBases;
    }

    private void dfs(String[] sh, int targ, int has, int cur) {
        if (has == targ) {
            vaList.add(va.toArray(String[]::new));
            return;
        }

        for (int i = 0; i < sh.length; i++) {
            va.add(sh[i]);
            dfs(sh, targ, has + 1, i);
            va.pop();
        }
    }
}
