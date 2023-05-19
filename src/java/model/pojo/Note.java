package model.pojo;

import java.sql.Date;

public class Note {

    private int idNote;
    private String title;
    private String content;
    private Date creationDate;
    private boolean deleted;
    private int idNotebook;
    private int idPriority;
    private int idUser;

    public Note() {
    }

    public Note(int idUser) {
        this.idUser = idUser;
    }

    public Note(int idNotebook, int idUser) {
        this.idNotebook = idNotebook;
        this.idUser = idUser;
    }

    public Note(String title, String content, int idNotebook, int idUser) {
        this.title = title;
        this.content = content;
        this.idNotebook = idNotebook;
        this.idUser = idUser;
    }

    public Note(int idNote, String title, String content, int idNotebook, int idPriority) {
        this.idNote = idNote;
        this.title = title;
        this.content = content;
        this.idNotebook = idNotebook;
        this.idPriority = idPriority;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getIdNotebook() {
        return idNotebook;
    }

    public void setIdNotebook(int idNotebook) {
        this.idNotebook = idNotebook;
    }

    public int getIdPriority() {
        return idPriority;
    }

    public void setIdPriority(int idPriority) {
        this.idPriority = idPriority;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}