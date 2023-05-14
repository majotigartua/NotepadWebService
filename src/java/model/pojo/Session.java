package model.pojo;

public class Session {

    private String accessToken;
    private String name;
    private String paternalSurname;
    private String maternalSurname;

    public Session() {
    }

    public Session(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    @Override
    public String toString() {
        return getName() + " " + getPaternalSurname() + " " + getMaternalSurname();
    }
    
}