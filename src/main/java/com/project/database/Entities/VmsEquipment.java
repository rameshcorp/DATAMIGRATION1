package com.project.database.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vms_equipment")
public class VmsEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eqid;

    @Column(name = "equipmentID", length = 255)
    private String equipmentId;

    @Column(name = "equipmentname", length = 255)
    private String equipmentName;

    @Column(name = "description")
    private String description;

    @Column(name = "model")
    private String model;

    @Column(name = "calibrationperiod")
    private String calibrationPeriod;

    @Column(name = "allowableperiod")
    private String allowablePeriod;

    @Column(name = "range")
    private String range;

    @Column(name = "calibrationmethod")
    private String calibrationmethod;

    @Column(name = "duereminderfrequency")
    private String duereminderfrequency;

    @Column(name = "tolerance")
    private String tolerance;

    @Column(name = "upcomingreminder")
    private String upcomingreminder;

    @Column(name = "department")
    private String department;

    @Column(name = "issuedate")
    private Date issuedate;

    @Column(name = "equipmentstatus")
    private String equipmentstatus;

    @Column(name = "nextpmdate")
    private Date nextpmdate;

    @Column(name = "nextpmdatestring")
    private String nextpmdatestring;

    @Column(name = "masterdata")
    private String masterdata;

    @Column(name = "mastercode")
    private String mastercode;

    @Column(name = "mastercalibration")
    private String mastercalibration;

    @Column(name = "departmentid")
    private Integer departmentid;

    @Column(name = "IsActive")
    private Integer IsActive;

    @Column(name = "receiveddate")
    private Date receiveddate;

    @Column(name = "CreatedDate")
    private Date CreatedDate;

    @Column(name = "ModifiedDate")
    private Timestamp ModifiedDate;

}
