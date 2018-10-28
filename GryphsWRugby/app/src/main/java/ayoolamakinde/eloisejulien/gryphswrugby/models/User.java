package ayoolamakinde.eloisejulien.gryphswrugby.models;


public class User {

    private String uid;
    private String username;
    private String password;
    private Boolean isCoach;

    public User() { }

    public User(String uid, String username) {
        this.uid = uid;
        this.username = username;
        this.isCoach = false;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public Boolean getIsCoach() { return isCoach; }

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setIsCoach(Boolean coach) { isCoach = coach; }
}
