package com.ftence.ftwekey.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Wiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long version;

    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String body;
    private String editor;

    @CreationTimestamp
    private LocalDateTime dateCreated;

}
