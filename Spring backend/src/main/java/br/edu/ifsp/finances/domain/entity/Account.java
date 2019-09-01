package br.edu.ifsp.finances.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private AccountType type;

    @OneToOne
    private User user;
}
