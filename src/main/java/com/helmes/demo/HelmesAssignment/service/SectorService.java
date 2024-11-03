package com.helmes.demo.HelmesAssignment.service;

import com.helmes.demo.HelmesAssignment.model.Sector;
import com.helmes.demo.HelmesAssignment.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class SectorService {

    @Autowired
    private final SectorRepository sectorRepository;

    public SectorService(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @GetMapping("/sectors")
    public List<Sector> getAllSectorDisplayData() {
        return sectorRepository.findAllTopLevelSectors();
    }
}
