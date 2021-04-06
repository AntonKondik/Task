package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountDto {

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String accountNumber;

    @EqualsAndHashCode.Include
    private String bic;

    private Double amount;
}
