package br.edu.ifsp.finances.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal amount;

    @OneToOne
    @JoinColumn(unique = true)
    private Account account;

    @OneToOne
    private TransactionCategory category;

    @OneToOne
    private User user;
}
