package model;

public class BloodRequest {
    private final String id;
    private final String patientName;
    private final String bloodGroup;
    private final int units;
    private final String date;
    private String status;

    public BloodRequest(String id, String patientName, String bloodGroup,
                        int units, String date, String status) {
        this.id          = id;
        this.patientName = patientName;
        this.bloodGroup  = bloodGroup;
        this.units       = units;
        this.date        = date;
        this.status      = status;
    }

    public String getId()           { return id; }
    public String getPatientName()  { return patientName; }
    public String getBloodGroup()   { return bloodGroup; }
    public int    getUnits()        { return units; }
    public String getDate()         { return date; }
    public String getStatus()       { return status; }
    public void   setStatus(String s) { this.status = s; }
}