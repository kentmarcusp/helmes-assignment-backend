package com.helmes.demo.HelmesAssignment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sector")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sector_id")
    private long sectorId;

    @Column(name = "sector_name")
    private String sectorName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Sector parentSector;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentSector", fetch = FetchType.LAZY)
    private List<Sector> childSectors;

    @Transient
    private Long parentSectorId;

    @PostLoad
    public void populateParentSectorId() {
        this.parentSectorId = (parentSector != null) ? parentSector.getSectorId() : null;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "sectorId=" + sectorId +
                ", sectorName='" + sectorName + '\'' +
                ", parentSectorId= '" + parentSectorId +
                '}';
    }


}