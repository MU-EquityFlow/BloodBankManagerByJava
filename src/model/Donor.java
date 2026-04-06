package model;

public class Donor {
    private String id;
    private String name;
    private String bloodGroup;
    private String phone;
    private String date;

    public Donor(String id, String name, String bloodGroup, String phone, String date) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.date = date;
    }

    public String getId()          { return id; }
    public String getName()        { return name; }
    public String getBloodGroup()  { return bloodGroup; }
    public String getPhone()       { return phone; }
    public String getDate()        { return date; }
}