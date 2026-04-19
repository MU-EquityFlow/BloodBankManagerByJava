package patterns.memento;
import java.util.LinkedHashMap;
import java.util.Map;
public class StockMemento {
    private final Map<String, Integer> state;
    public StockMemento(Map<String, Integer> state) {
        this.state = new LinkedHashMap<>(state);
    }
    public Map<String, Integer> getState() {
        return new LinkedHashMap<>(state);
    }
}