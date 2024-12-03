package org.example;

/**
 * sima osztály ami név/szimbolum párostitást tartalmaz
 * pl.: Apple/AAPL
 */
public class Asset {
    private String name;
    private String Symbol;

    /**
     *
     * @param name név
     * @param Symbol szimbolum
     */
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
