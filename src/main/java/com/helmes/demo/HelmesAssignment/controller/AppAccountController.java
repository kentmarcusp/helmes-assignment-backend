package com.helmes.demo.HelmesAssignment.controller;

import com.helmes.demo.HelmesAssignment.dto.AppAccountSectorDTO;
import com.helmes.demo.HelmesAssignment.model.AppAccount;
import com.helmes.demo.HelmesAssignment.service.AppAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/appAccount")
public class AppAccountController {

    @Autowired
    private final AppAccountService appAccountService;

    public AppAccountController(AppAccountService appAccountService) {
        this.appAccountService = appAccountService;
    }


    @GetMapping("/{appAccountId}")
    public ResponseEntity<AppAccount> getAppAccountDataById(@PathVariable Long appAccountId) {
        try {
            AppAccount accountData = appAccountService.getAppAccountDataById(appAccountId);

            return ResponseEntity.ok(accountData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{appAccountId}/sectors")
    public ResponseEntity<AppAccount> updateAppAccountSectors(@PathVariable Long appAccountId,
            @RequestBody AppAccountSectorDTO dto) {

        if (!appAccountId.equals(dto.getAppAccountId())) {
            throw new IllegalArgumentException(String.format("AppAccountId does not match id within request body: %d and %d",
                            appAccountId, dto.getAppAccountId()));
        }

        appAccountService.updateAppAccountSectors(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/sectors/{id}")
    public ResponseEntity<AppAccountSectorDTO> getPreselectedSectorsById(@PathVariable Long id) {
        try {
            AppAccountSectorDTO appAccountSectorDTO = appAccountService.getPreselectedDataById(id);

            return ResponseEntity.ok(appAccountSectorDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
