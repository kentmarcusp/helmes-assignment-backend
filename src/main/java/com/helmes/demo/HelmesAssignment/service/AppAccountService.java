package com.helmes.demo.HelmesAssignment.service;

import com.helmes.demo.HelmesAssignment.dto.AppAccountSectorDTO;
import com.helmes.demo.HelmesAssignment.model.AppAccount;
import com.helmes.demo.HelmesAssignment.model.Sector;
import com.helmes.demo.HelmesAssignment.repository.AppAccountRepository;
import com.helmes.demo.HelmesAssignment.repository.SectorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppAccountService {

    @Autowired
    private final AppAccountRepository appAccountRepository;

    @Autowired
    private final SectorRepository sectorRepository;

    public AppAccountService(AppAccountRepository appAccountRepository,
                             SectorRepository sectorRepository) {
        this.appAccountRepository = appAccountRepository;
        this.sectorRepository = sectorRepository;
    }

    public AppAccount getAppAccountDataById(Long id) {
        AppAccount appAccount = appAccountRepository.findByIdWithSelectedSectors(id)
                .orElseThrow(() -> new EntityNotFoundException("AppAccount not found with id: " + id));


        for (Sector sector : appAccount.getSectors()) {
            sector.setChildSectors(new ArrayList<>());
        }

        mapAndFilterParentSectors(appAccount);
        return appAccount;
    }

    public AppAccountSectorDTO getPreselectedDataById(Long id) {
        AppAccount appAccount = appAccountRepository.findByIdWithSelectedSectors(id)
                .orElseThrow(() -> new EntityNotFoundException("AppAccount not found with id: " + id));

        Set<Long> preSelectedIds = getExistingSectorIds(appAccount);

        String userName = appAccount.getAppAccountName();
        Boolean hasAgreed = appAccount.getAgreeToTerms();

        AppAccountSectorDTO appAccountSectorDTO = new AppAccountSectorDTO();
        appAccountSectorDTO.setUserName(userName);
        appAccountSectorDTO.setHasAgreed(hasAgreed);
        appAccountSectorDTO.setSelectedSectorIds(preSelectedIds);

        return appAccountSectorDTO;
    }


    public void mapAndFilterParentSectors(AppAccount appAccount) {
        // Firstly we clear the child sectors and create a new map of sectors by sectorId
        Map<Long, Sector> sectorMap = appAccount.getSectors().stream()
                .peek(sector -> sector.setChildSectors(new ArrayList<>()))
                .collect(Collectors.toMap(Sector::getSectorId, sector -> sector));

        // Secondly manually populate child sectors based on their existing parentSectorId-s
        for (Sector sector : appAccount.getSectors()) {
            Long parentId = sector.getParentSectorId();
            if (parentId != null && sectorMap.containsKey(parentId)) {
                // Here we find parent sector and add current sector as a child
                Sector parentSector = sectorMap.get(parentId);
                parentSector.getChildSectors().add(sector);
            }
        }

        // Lastly I out top-most level sectors that do not contain a parent sector within accounts sectors
        List<Sector> parentSectors = appAccount.getSectors().stream()
                .filter(sector -> sector.getParentSectorId() == null)
                .collect(Collectors.toList());

        appAccount.setSectors(parentSectors);
    }


    public void updateAppAccountSectors(AppAccountSectorDTO dto) {
        validateAppAccountSectorDTOData(dto);

        Long appAccountId = dto.getAppAccountId();
        Set<Long> newSectorIds = dto.getSectors();
        Set<Long> allSectorIds = new HashSet<>(newSectorIds);

        for (Long sectorId : newSectorIds) {
            collectLinkedParentSectorIds(sectorId, allSectorIds);
        }

        AppAccount appAccount = appAccountRepository.findById(appAccountId)
                .orElseThrow(() -> new EntityNotFoundException("AppAccount not found with id: " + appAccountId));

        Set<Long> existingSectorIds = getExistingSectorIds(appAccount);

        // Compare whether any new sectors are already present or should they be removed
        Set<Long> sectorsToAdd = compareSectorExistence(allSectorIds, existingSectorIds);
        Set<Long> sectorsToRemove = compareSectorExistence(existingSectorIds, allSectorIds);

        Set<Sector> sectorsToSave = new HashSet<>();
        saveNewlySelectedSectors(sectorsToAdd, sectorsToSave);

        // Compare sectorsToRemove against existing sectors, to clean out unnecessary content
        appAccount.getSectors().removeIf(sector -> sectorsToRemove.contains(sector.getSectorId()));
        appAccount.getSectors().addAll(sectorsToSave);

        appAccount.setAppAccountName(dto.getUserName());

        if (dto.getHasAgreed()) {
            appAccount.setAgreeToTerms(Boolean.TRUE);
            appAccount.setDateAgreed(LocalDateTime.now());
        } else {
            appAccount.setAgreeToTerms(Boolean.FALSE);
        }

        appAccountRepository.save(appAccount);
    }

    // This is a helper method recursively to find all parent sectors of any selected sectors in case they might be
    // nested and/or linked to another parent sector which might not have been chosen by the user themselves
    private void collectLinkedParentSectorIds(Long sectorId, Set<Long> parentIds) {
        Long parentId = sectorRepository.findParentIdBySectorId(sectorId);
        if (parentId != null) {
            parentIds.add(parentId);
            collectLinkedParentSectorIds(parentId, parentIds);
        }
    }

    private void validateAppAccountSectorDTOData(AppAccountSectorDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Request DTO body cannot be null.");
        }

        if (dto.getSectors() == null || dto.getSectors().isEmpty()) {
            throw new IllegalArgumentException("At least one sector must be selected.");
        }
    }

    // Add the result of the filtered sectors into the already existing sector list
    private void saveNewlySelectedSectors(Set<Long> sectorsToAdd, Set<Sector> sectorsToSave) {
        for (Long id : sectorsToAdd) {
            Sector sector = sectorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Sector not found with id: " + id));
            sectorsToSave.add(sector);
        }
    }

    // Helper method for easily fetching existing app_account and sector related data
    private static Set<Long> getExistingSectorIds(AppAccount appAccount) {
        Set<Long> existingSectorIds = new HashSet<>();
        for (Sector sector : appAccount.getSectors()) {
            Long sectorId = sector.getSectorId();
            existingSectorIds.add(sectorId);
        }
        return existingSectorIds;
    }

    private Set<Long> compareSectorExistence(Set<Long> allSectorIds, Set<Long> existingSectorIds) {
        Set<Long> sectorsToAdd = new HashSet<>(allSectorIds);
        sectorsToAdd.removeAll(existingSectorIds);
        return sectorsToAdd;
    }
}
