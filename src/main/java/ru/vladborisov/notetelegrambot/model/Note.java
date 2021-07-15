package ru.vladborisov.notetelegrambot.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @Column(name = "note_tags")
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "note_tags",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> noteTags;

    @Column(name = "note_content")
    private String noteContent;

    public Note(Set<Tag> noteTags, String noteContent) {
        this.noteTags = noteTags;
        this.noteContent = noteContent;
    }
}
