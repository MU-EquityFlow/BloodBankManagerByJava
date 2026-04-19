package patterns.command;

import patterns.data.JsonStorage;
import model.BloodRequest;

public class RequestBloodCommand implements Command {
    private final BloodRequest request;
    private final JsonStorage  storage = JsonStorage.getInstance();

    public RequestBloodCommand(BloodRequest request) { this.request = request; }

    @Override public void execute() { storage.addRequest(request); }
    @Override public void undo()    { storage.removeRequest(request.getId()); }
}