package com.project.database.Entities;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vms_equipment_pmpic")
public class vms_equipment_pmpic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paraid;

    @Column(name = "eqid", length = 255)
    private String eqid;

    @Column(name = "piclist")
    private String piclist;

    @Column(name = "nextpmdate'")
    private Date nextpmdate;

    @Column(name = "IsActive")
    private Integer IsActive;

    @Column(name = "user_id", length = 255)
    private String user_id;

    @Column(name = "eqids", length = 255)
    private String eqids;
}
