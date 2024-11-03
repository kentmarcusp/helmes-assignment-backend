package com.helmes.demo.HelmesAssignment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppAccountSectorDTO {
    private Long appAccountId;
    private String userName;
    private Set<Long> sectors = new HashSet<>();
    private Boolean hasAgreed;
    @Nullable
    private Set<Long> selectedSectorIds = new HashSet<>();
}