package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    public Integer noteId;
    public Integer userId;
    public String noteTitle;
    public String noteDescription;

    public Note() {
    }

    public Note(Integer userId, String noteTitle, String noteDescription) {
        this.userId = userId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
