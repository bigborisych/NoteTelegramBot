package ru.vladborisov.notetelegrambot.dao;

import ru.vladborisov.notetelegrambot.model.Note;
import ru.vladborisov.notetelegrambot.model.Tag;

import java.util.List;
import java.util.Set;

public interface NotesDao {
    Note addNote(Note note);
    Tag addTag(Tag tag);
    Tag getTagByName(String name);
    List<Note> getNoteWithTags(Set<Tag> tags);
    List<Note> getNotes();
    Set<Tag> getTags();
}
