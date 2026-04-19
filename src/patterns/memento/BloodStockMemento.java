package patterns.memento;

import java.util.LinkedHashMap;
import java.util.Map;

public class BloodStockMemento {
    private final Map<String, Integer> state;

    public BloodStockMemento(Map<String, Integer> state) {
        this.state = new LinkedHashMap<>(state);
    }

    public Map<String, Integer> getState() { return new LinkedHashMap<>(state); }
}