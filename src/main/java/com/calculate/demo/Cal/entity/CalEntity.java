package com.calculate.demo.Cal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Calculate")
public class CalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String keyA;
    @Column
    private String value;
    public CalEntity(String keyA, String value) {
        this.keyA = keyA;
        this.value = value;
    }
}
