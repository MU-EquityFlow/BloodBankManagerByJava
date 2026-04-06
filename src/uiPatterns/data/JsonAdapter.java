package uiPatterns.data;

import model.BloodRequest;
import model.BloodStock;
import model.Donor;

import java.util.*;
import java.util.regex.*;

public class JsonAdapter implements DataConverter {

    @Override
    public String donorsToJson(List<Donor> donors) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < donors.size(); i++) {
            Donor d = donors.get(i);
            sb.append("{")
              .append(str("id",         d.getId()))         .append(",")
              .append(str("name",       d.getName()))       .append(",")
              .append(str("bloodGroup", d.getBloodGroup())).append(",")
              .append(str("phone",      d.getPhone()))      .append(",")
              .append(str("date",       d.getDate()))
              .append("}");
            if (i < donors.size() - 1) sb.append(",");
        }
        return sb.append("]").toString();
    }

    @Override
    public List<Donor> jsonToDonors(String json) {
        List<Donor> list = new ArrayList<>();
        if (json == null || json.trim().length() < 2) return list;
        for (Map<String, String> obj : parseArray(json))
            list.add(new Donor(obj.get("id"), obj.get("name"),
                               obj.get("bloodGroup"), obj.get("phone"), obj.get("date")));
        return list;
    }

    @Override
    public String requestsToJson(List<BloodRequest> requests) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < requests.size(); i++) {
            BloodRequest r = requests.get(i);
            sb.append("{")
              .append(str("id",          r.getId()))          .append(",")
              .append(str("patientName", r.getPatientName())).append(",")
              .append(str("bloodGroup",  r.getBloodGroup()))  .append(",")
              .append("\"units\":").append(r.getUnits())      .append(",")
              .append(str("date",        r.getDate()))        .append(",")
              .append(str("status",      r.getStatus()))
              .append("}");
            if (i < requests.size() - 1) sb.append(",");
        }
        return sb.append("]").toString();
    }

    @Override
    public List<BloodRequest> jsonToRequests(String json) {
        List<BloodRequest> list = new ArrayList<>();
        if (json == null || json.trim().length() < 2) return list;
        for (Map<String, String> obj : parseArray(json))
            list.add(new BloodRequest(
                obj.get("id"), obj.get("patientName"), obj.get("bloodGroup"),
                Integer.parseInt(obj.getOrDefault("units", "1")),
                obj.get("date"), obj.get("status")));
        return list;
    }

    @Override
    public String stockToJson(BloodStock stock) {
        StringBuilder sb = new StringBuilder("{");
        Map<String, Integer> all = stock.getAll();
        int i = 0, size = all.size();
        for (Map.Entry<String, Integer> e : all.entrySet()) {
            sb.append("\"").append(e.getKey()).append("\":").append(e.getValue());
            if (++i < size) sb.append(",");
        }
        return sb.append("}").toString();
    }

    @Override
    public BloodStock jsonToStock(String json) {
        BloodStock stock = new BloodStock();
        if (json == null || json.trim().length() < 2) return stock;
        Matcher m = Pattern.compile("\"([^\"]+)\":(\\d+)").matcher(json);
        while (m.find()) stock.set(m.group(1), Integer.parseInt(m.group(2)));
        return stock;
    }

    private String str(String key, String value) {
        return "\"" + key + "\":\"" + (value == null ? "" : value.replace("\"", "'")) + "\"";
    }

    private List<Map<String, String>> parseArray(String json) {
        List<Map<String, String>> result = new ArrayList<>();
        int depth = 0, start = -1;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') { if (depth++ == 0) start = i; }
            else if (c == '}' && --depth == 0 && start != -1) {
                result.add(parseObject(json.substring(start + 1, i)));
                start = -1;
            }
        }
        return result;
    }

    private Map<String, String> parseObject(String obj) {
        Map<String, String> map = new LinkedHashMap<>();
        Matcher sm = Pattern.compile("\"([^\"]+)\":\"([^\"]*)\"").matcher(obj);
        while (sm.find()) map.put(sm.group(1), sm.group(2));
        Matcher nm = Pattern.compile("\"([^\"]+)\":(\\d+)").matcher(obj);
        while (nm.find()) map.putIfAbsent(nm.group(1), nm.group(2));
        return map;
    }
}