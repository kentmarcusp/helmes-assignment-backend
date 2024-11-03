package com.helmes.demo.HelmesAssignment.repository;

import com.helmes.demo.HelmesAssignment.model.Sector;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query("SELECT s FROM Sector s LEFT JOIN FETCH s.childSectors c WHERE s.parentSector IS NULL")
    List<Sector> findAllTopLevelSectors();

    @Query("SELECT s.parentSector.sectorId FROM Sector s WHERE s.sectorId = :sectorId")
    Long findParentIdBySectorId(@Param("sectorId") Long sectorId);

}
