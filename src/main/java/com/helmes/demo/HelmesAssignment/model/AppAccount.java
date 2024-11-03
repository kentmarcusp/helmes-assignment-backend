package com.helmes.demo.HelmesAssignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "app_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_account_id")
    private long appAccountId;

    @Column(name = "app_account_name", nullable = false)
    private String appAccountName;

    @Column(name = "agree_to_terms", nullable = false)
    private Boolean agreeToTerms;

    @Column(name = "date_agreed")
    private LocalDateTime dateAgreed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "app_account_sector",
            joinColumns = @JoinColumn(name = "app_account_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private List<Sector> sectors = new ArrayList<>();
}
