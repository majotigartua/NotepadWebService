package model.pojo;

import java.time.LocalDateTime;

public class Note {
    
    private int idNote;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private boolean deleted;
    private int idNotebook;
    private int idPriority;
    private int idUser;

    public Note() {
    }

    public Note(String title, String content, LocalDateTime creationDate) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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