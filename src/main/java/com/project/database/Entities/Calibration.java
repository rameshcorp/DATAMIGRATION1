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
@Table(name = "calibration_schedules")
public class Calibration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer calibration_schedule_id;

    @Column(name = "equipment_serial_number")
    private Integer equipment_serial_number;

    @Column(name = "calibration_email_tracking_id")
    private Integer calibration_email_tracking_id;

    @Column(name = "status")
    private String status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "next_calibration_date")
    private String next_calibration_date;

    @Column(name = "first_issue_date")
    private String first_issue_date;

    @Column(name = "calibration_type")
    private String calibration_type;

    @Column(name = "status_updated_at")
    private String status_updated_at;

    @Column(name = "created_by")
    private String created_by;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "active")
    private Integer active;

    @Column(name = "calibration_schedules_logs_id")
    private Integer calibration_schedules_logs_id;

    @Column(name = "updated_by")
    private String updated_by;

    @Column(name = "updated_at")
    private Date updated_at;

}
