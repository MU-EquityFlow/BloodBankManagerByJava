package patterns.data;

import model.BloodRequest;
import model.BloodStock;
import model.Donor;
import java.util.List;

public interface DataConverter {
    String         donorsToJson(List<Donor> donors);
    List<Donor>    jsonToDonors(String json);
    String         requestsToJson(List<BloodRequest> requests);
    List<BloodRequest> jsonToRequests(String json);
    String         stockToJson(BloodStock stock);
    BloodStock     jsonToStock(String json);
}