package com.project.database.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vms_equipment_specrange")
public class VmsEquipmentSpecrange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paraid;

    @Column(name = "eqid", length = 255)
    private String eqid;

    @Column(name = "speclist", columnDefinition = "TEXT")
    private String speclist;

    @Column(name = "nextpmdate")
    private Date nextpmdate;

    @Column(name = "IsActive")
    private Integer isActive;

    // Getters and setters...
}
