package patterns.data;

import model.BloodRequest;
import model.BloodStock;
import model.Donor;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {
    private static JsonStorage instance;

    private static final String DIR           = "storage";
    private static final String DONORS_FILE   = "storage/donors.json";
    private static final String STOCK_FILE    = "storage/stock.json";
    private static final String REQUESTS_FILE = "storage/requests.json";

    private final DataConverter adapter = new JsonAdapter();
    private List<Donor>        donors;
    private BloodStock         stock;
    private List<BloodRequest> requests;

    private JsonStorage() {
        new File(DIR).mkdirs();
        donors   = adapter.jsonToDonors(read(DONORS_FILE));
        stock    = adapter.jsonToStock(read(STOCK_FILE));
        requests = adapter.jsonToRequests(read(REQUESTS_FILE));
    }

    public static JsonStorage getInstance() {
        if (instance == null) instance = new JsonStorage();
        return instance;
    }

    public void addDonor(Donor donor) {
        donors.add(donor);
        stock.add(donor.getBloodGroup(), 1);
        persistAll();
    }

    public void removeDonor(Donor donor) {
        donors.removeIf(d -> d.getId().equals(donor.getId()));
        stock.deduct(donor.getBloodGroup(), 1);
        persistAll();
    }

    public void clearAllDonors() {
        donors.clear();
        persistAll();
    }

    public List<Donor> getDonors() { return new ArrayList<>(donors); }

    public BloodStock getStock() { return stock.copy(); }

    public void saveStock(BloodStock updated) {
        this.stock = updated.copy();
        write(STOCK_FILE, adapter.stockToJson(stock));
    }

    public void addRequest(BloodRequest req) {
        requests.add(req);
        write(REQUESTS_FILE, adapter.requestsToJson(requests));
    }

    public void removeRequest(String id) {
        requests.removeIf(r -> r.getId().equals(id));
        write(REQUESTS_FILE, adapter.requestsToJson(requests));
    }

    public List<BloodRequest> getRequests() { return new ArrayList<>(requests); }

    public boolean fulfillRequest(String requestId) {
        for (BloodRequest r : requests) {
            if (r.getId().equals(requestId) && r.getStatus().equals("Pending")) {
                if (stock.deduct(r.getBloodGroup(), r.getUnits())) {
                    r.setStatus("Fulfilled");
                    persistAll();
                    return true;
                }
            }
        }
        return false;
    }

    private void persistAll() {
        write(DONORS_FILE,   adapter.donorsToJson(donors));
        write(STOCK_FILE,    adapter.stockToJson(stock));
        write(REQUESTS_FILE, adapter.requestsToJson(requests));
    }

    private String read(String path) {
        try {
            File f = new File(path);
            return f.exists() ? new String(Files.readAllBytes(f.toPath())) : "";
        } catch (IOException e) { return ""; }
    }

    private void write(String path, String content) {
        try { Files.write(Paths.get(path), content.getBytes()); }
        catch (IOException e) { e.printStackTrace(); }
    }
}