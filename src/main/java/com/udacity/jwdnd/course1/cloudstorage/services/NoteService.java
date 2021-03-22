package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(NoteForm note) {
        return noteMapper.addNote(new Note(note.userId, note.noteTitle, note.noteDescription));
    }

    public List<Note> getNotesForUser(Integer userId) {
        return noteMapper.getAllNotesForUser(userId);
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }

    public int updateNote(NoteForm note) {
        Note toUpdate = noteMapper.getNote(note.getNoteId());
        toUpdate.setNoteDescription(note.getNoteDescription());
        toUpdate.setNoteTitle(note.getNoteTitle());
        return noteMapper.updateNote(toUpdate);
    }
}
