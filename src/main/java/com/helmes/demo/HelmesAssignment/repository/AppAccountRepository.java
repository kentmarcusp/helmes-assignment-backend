package com.helmes.demo.HelmesAssignment.repository;

import com.helmes.demo.HelmesAssignment.model.AppAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppAccountRepository extends JpaRepository<AppAccount, Long> {

    @Query("SELECT a FROM AppAccount a LEFT JOIN a.sectors s WHERE a.appAccountId = :id")
    Optional<AppAccount> findByIdWithSelectedSectors(@Param("id") Long id);

    @Query("SELECT a FROM AppAccount a WHERE LOWER(a.appAccountName) = LOWER(:appAccountName)")
    Optional<AppAccount> findByUsernameIgnoreCase(@Param("appAccountName") String username);
}
