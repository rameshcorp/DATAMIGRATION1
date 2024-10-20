package com.project.database.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equipments1")
public class Equipments1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentSerialNumber;

    @Column(name = "equipment_id", length = 255)
    private String equipmentId;

    @Column(name = "equipment_type_id")
    private Integer equipmentTypeID;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "active")
    private Integer isActive;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "activated_date")
    private String activatedDate;

    @Column(name = "deactivated_date")
    private String deactivatedDate;

    @Column(name = "isdelete")
    private String isDelete;

    @Column(name = "document_url")
    private String documentUrl;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    // Getters and setters...
}
