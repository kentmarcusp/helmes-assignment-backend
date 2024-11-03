package com.helmes.demo.HelmesAssignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.helmes.demo.HelmesAssignment.dto.AppAccountSectorDTO;
import com.helmes.demo.HelmesAssignment.model.AppAccount;
import com.helmes.demo.HelmesAssignment.model.Sector;
import com.helmes.demo.HelmesAssignment.repository.AppAccountRepository;
import com.helmes.demo.HelmesAssignment.service.AppAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AppAccountServiceTest {

    @InjectMocks
    private AppAccountService appAccountService;

    @Mock
    private AppAccountRepository appAccountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPreselectedDataById_shouldReturnAppAccountData() {
        AppAccount mockAppAccount = generateBasicAppAccountData(22L);

        when(appAccountRepository.findByIdWithSelectedSectors(22L)).thenReturn(Optional.of(mockAppAccount));

        AppAccountSectorDTO result = appAccountService.getPreselectedDataById(22L);

        assertEquals("Helmes", result.getUserName());
        assertEquals(Boolean.TRUE, result.getHasAgreed());
    }

    @Test
    public void mapAndFilterParentSectors_shouldValidateParentSectorExistence() {
        Sector childSector1 = new Sector();
        Sector childSector2 = new Sector();
        Sector parentSector = new Sector();

        childSector1.setSectorId(1L);
        childSector1.setParentSectorId(3L);
        childSector2.setSectorId(2L);
        childSector2.setParentSectorId(null);

        parentSector.setSectorId(3L);
        parentSector.setParentSectorId(null);

        AppAccount appAccount = generateBasicAppAccountData(1L);
        appAccount.setSectors(Arrays.asList(childSector1, childSector2, parentSector));

        appAccountService.mapAndFilterParentSectors(appAccount);

        List<Sector> filteredSectors = appAccount.getSectors();
        assertEquals(2, filteredSectors.size());
        assertTrue(filteredSectors.contains(parentSector));

        List<Sector> childSectors = parentSector.getChildSectors();
        assertEquals(1, childSectors.size());
        assertTrue(childSectors.contains(childSector1));
        assertEquals(2, appAccount.getSectors().size());
    }


    // Stock data generators
    private AppAccount generateBasicAppAccountData(Long appAccountId) {
        AppAccount mockAppAccount = new AppAccount();
        mockAppAccount.setAppAccountId(appAccountId);
        mockAppAccount.setAppAccountName("Helmes");
        mockAppAccount.setAgreeToTerms(true);

        return mockAppAccount;
    }

}
