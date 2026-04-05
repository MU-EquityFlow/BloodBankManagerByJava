package model;

import memento.BloodStockMemento;
import java.util.LinkedHashMap;
import java.util.Map;

public class BloodStock {
    private Map<String, Integer> units;
    private static final String[] ALL_GROUPS =
        {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public BloodStock() {
        units = new LinkedHashMap<>();
        for (String g : ALL_GROUPS) units.put(g, 0);
    }

    public BloodStock(Map<String, Integer> data) {
        this.units = new LinkedHashMap<>(data);
    }

    public int get(String group)          { return units.getOrDefault(group, 0); }
    public void set(String group, int n)  { units.put(group, n); }
    public void add(String group, int n)  { units.merge(group, n, Integer::sum); }

    public boolean deduct(String group, int n) {
        int current = units.getOrDefault(group, 0);
        if (current < n) return false;
        units.put(group, current - n);
        return true;
    }

    public Map<String, Integer> getAll() { return new LinkedHashMap<>(units); }
    public BloodStock copy()             { return new BloodStock(units); }

    public BloodStockMemento createMemento() {
        return new BloodStockMemento(new LinkedHashMap<>(units));
    }

    public void restore(BloodStockMemento m) {
        this.units = new LinkedHashMap<>(m.getState());
    }
}