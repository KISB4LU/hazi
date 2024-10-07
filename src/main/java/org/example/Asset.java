package org.example;

public class Asset {
    private String name;
    private String Symbol;
    public Asset(String name, String Symbol) {
        this.name = name;
        this.Symbol = Symbol;
    }
    public String getName() {
        return name;
    }
    public String getSymbol() {
        return Symbol;
    }
}
