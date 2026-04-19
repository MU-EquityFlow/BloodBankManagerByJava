package patterns.memento;
import java.util.Stack;
public class Stocking {
    private static Stocking instance;
    private final Stack<StockMemento> history = new Stack<>();
    private Stocking() {}

    public static Stocking getInstance() {
        if (instance == null) instance = new Stocking();
        return instance;
    }

    public void save(StockMemento mem) { history.push(mem); }

    public StockMemento undo() {
        return history.isEmpty() ? null : history.pop();
    }

    public boolean canUndo() { return !history.isEmpty(); }
}