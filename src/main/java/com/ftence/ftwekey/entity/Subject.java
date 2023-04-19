package com.ftence.ftwekey.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer circle;
    private String name;

    @Column(name = "black_hole_min")
    private Integer blackHoleMin;

    @Column(name = "black_hole_max")
    private Integer blackHoleMax;
    private Long wikiVersion;
    private String info;
    private String description;
    @Column(name = "pdf_url")
    private String pdfUrl;
}
