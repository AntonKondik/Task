package com.example.demo.model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name="ACCOUNT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountIdGenerator")
    @SequenceGenerator(name = "AccountIdGenerator", sequenceName = "account_id_seq", initialValue = 1, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @EqualsAndHashCode.Include
    @Column(name = "BIC", nullable = false)
    private String bic;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
}
