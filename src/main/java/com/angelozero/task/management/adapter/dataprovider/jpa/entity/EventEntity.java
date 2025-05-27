package com.angelozero.task.management.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_info", schema = "public")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String eventType;

    private Integer entityId;

    private Integer userId;

    private LocalDateTime localDateTime;

    private boolean read;

    private String message;

}