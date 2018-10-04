package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue
    private int id;

    private byte[] content;

    private String name;

    @ManyToOne
    private Bug bug;

    public Attachment(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }
}
