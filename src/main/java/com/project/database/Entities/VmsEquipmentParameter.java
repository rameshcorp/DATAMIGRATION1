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
@Table(name = "vms_equipment_parameter")
public class VmsEquipmentParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paraid;

    @Column(name = "eqid", length = 255)
    private String eqid;

    @Column(name = "parameterlist", length = 255)
    private String parameterlist;

    @Column(name = "nextpmdate")
    private Date nextpmdate;

    @Column(name = "IsActive")
    private Integer isActive;

    @Column(name = "equipment_type_id")
    private Integer equipmentTypeId;

    // Getters and setters...
}

