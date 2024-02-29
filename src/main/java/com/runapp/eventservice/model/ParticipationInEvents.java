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
@Table(name = "participation_in_events")
public class ParticipationInEvents implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "Event_id")
    @ManyToOne
    private Event event;
    @JoinColumn(name = "User_id")
    private Long userId;
}
