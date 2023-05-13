package model.pojo;

public class Priority {
    
    private int idPriority;
    private String name;

    public Priority() {
    }

    public Priority(String name) {
        this.name = name;
    }

    public int getIdPriority() {
        return idPriority;
    }

    public void setIdPriority(int idPriority) {
        this.idPriority = idPriority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}