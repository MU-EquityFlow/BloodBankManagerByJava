package patterns.memento;

import java.util.Stack;

public class StockCaretaker {
    private static StockCaretaker instance;
    private final Stack<BloodStockMemento> history = new Stack<>();

    private StockCaretaker() {}

    public static StockCaretaker getInstance() {
        if (instance == null) instance = new StockCaretaker();
        return instance;
    }

    public void save(BloodStockMemento m) { history.push(m); }

    public BloodStockMemento undo() {
        return history.isEmpty() ? null : history.pop();
    }

    public boolean canUndo() { return !history.isEmpty(); }
}