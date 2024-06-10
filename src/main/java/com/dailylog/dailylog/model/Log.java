package com.dailylog.dailylog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String store;
    private String description;
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "Categoria")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "FormaDePago")
    private PaymentMethod paymentMethod;
}

