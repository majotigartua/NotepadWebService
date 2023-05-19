package model.pojo;

public class Notebook {
    
    private int idNotebook;
    private String name;
    private String hexadecimalColor;
    private int idUser;

    public Notebook() {
    } 

    public Notebook(String name, String hexadecimalColor) {
        this.name = name;
        this.hexadecimalColor = hexadecimalColor;
    }

    public Notebook(int idNotebook, String name, String hexadecimalColor) {
        this.idNotebook = idNotebook;
        this.name = name;
        this.hexadecimalColor = hexadecimalColor;
    }

    public Notebook(String name, String hexadecimalColor, int idUser) {
        this.name = name;
        this.hexadecimalColor = hexadecimalColor;
        this.idUser = idUser;
    }

    public int getIdNotebook() {
        return idNotebook;
    }

    public void setIdNotebook(int idNotebook) {
        this.idNotebook = idNotebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHexadecimalColor() {
        return hexadecimalColor;
    }

    public void setHexadecimalColor(String hexadecimalColor) {
        this.hexadecimalColor = hexadecimalColor;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
}