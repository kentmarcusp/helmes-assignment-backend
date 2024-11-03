package com.helmes.demo.HelmesAssignment.controller;

import com.helmes.demo.HelmesAssignment.model.Sector;
import com.helmes.demo.HelmesAssignment.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin
@RestController
public class SectorController {

    @Autowired
    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping("/sectors")
    public List<Sector> getAllSectors() {
        return sectorService.getAllSectorDisplayData();
    }
}
