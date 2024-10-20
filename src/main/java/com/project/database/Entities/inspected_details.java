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
@Table(name = "inspected_details")
public class inspected_details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inspected_details_id;

    @Column(name = "sort_number")
    private Integer sortNumber;

    @Column(name = "feature_inspector")
    private String featureInspector;

    @Column(name = "spec_range")
    private String specRange;

    @Column(name = "type")
    private String type;

    @Column(name = "eqid")
    private String eqid;

    @Column(name = "equipment_type_id")
    private Integer equipmentTypeId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name= "active")
    private Integer isActive;
    // Getters and setters...
}
