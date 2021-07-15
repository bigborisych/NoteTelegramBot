package ru.vladborisov.notetelegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladborisov.notetelegrambot.dao.NotesDao;
import ru.vladborisov.notetelegrambot.model.Note;
import ru.vladborisov.notetelegrambot.model.Tag;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesDao notesDao;

    @Override
    public Note addNote(Note note) {
        return notesDao.addNote(note);
    }

    @Override
    public Tag addTag(Tag tag) {
        return notesDao.addTag(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return notesDao.getTagByName(name);
    }

    @Override
    public List<Note> getNoteWithTags(Set<Tag> tags) {
        return notesDao.getNoteWithTags(tags);
    }

    @Override
    public List<Note> getNotes() {
        return notesDao.getNotes();
    }

    @Override
    public Set<Tag> getTags() {
        return notesDao.getTags();
    }
}
