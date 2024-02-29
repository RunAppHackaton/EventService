package com.runapp.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event_type")
public class EventType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @Column(name = "event_type_name")
    private String eventTypeName;

    public EventType(Long id) {
        this.id = id;
    }
}
