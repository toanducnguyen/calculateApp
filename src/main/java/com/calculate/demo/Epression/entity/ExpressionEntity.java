package com.calculate.demo.Epression.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ExpressionTable")
public class ExpressionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String date;
    @Column
    private String keyExpression;
    @Column
    private int keyValue;

    public ExpressionEntity(String date, String keyExpression, int keyValue) {
        this.date = date;
        this.keyExpression = keyExpression;
        this.keyValue = keyValue;
    }
}


