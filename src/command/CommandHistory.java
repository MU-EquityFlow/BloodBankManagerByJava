package command;

import java.util.Stack;

public class CommandHistory {
    private static CommandHistory instance;
    private final Stack<Command> history = new Stack<>();

    private CommandHistory() {}

    public static CommandHistory getInstance() {
        if (instance == null) instance = new CommandHistory();
        return instance;
    }

    public void execute(Command cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public void undo() {
        if (!history.isEmpty()) history.pop().undo();
    }

    public boolean canUndo() { return !history.isEmpty(); }
}