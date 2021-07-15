package ru.vladborisov.notetelegrambot.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vladborisov.notetelegrambot.model.Note;
import ru.vladborisov.notetelegrambot.model.Tag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class NotesDaoImpl implements NotesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Note addNote(Note note) {
        entityManager.persist(note);
        return note;
    }

    @Override
    public Tag addTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Tag getTagByName(String name) {
        return entityManager.createQuery("from Tag tag where tagName=:name", Tag.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Note> getNoteWithTags(Set<Tag> tags) {
        return entityManager.createQuery("from Note note where noteTags=:tags", Note.class).setParameter("tags", tags).getResultList();
    }

    @Override
    public List<Note> getNotes() {
        return entityManager.createQuery("from Note", Note.class).getResultList();
    }

    @Override
    public Set<Tag> getTags() {
        return entityManager.createQuery("from Tag", Tag.class).getResultStream().collect(Collectors.toSet());
    }
}
