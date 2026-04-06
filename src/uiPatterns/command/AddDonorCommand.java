package uiPatterns.command;

import uiPatterns.data.JsonStorage;
import model.Donor;

public class AddDonorCommand implements Command {
    private final Donor donor;
    private final JsonStorage storage = JsonStorage.getInstance();

    public AddDonorCommand(Donor donor) { this.donor = donor; }

    @Override public void execute() { storage.addDonor(donor); }
    @Override public void undo()    { storage.removeDonor(donor); }
}