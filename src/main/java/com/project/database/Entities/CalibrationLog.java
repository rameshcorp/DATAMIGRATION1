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
@Table(name = "calibration_schedules_logs")
public class CalibrationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer calibration_schedule_log_id;

    @Column(name = "calibration_schedule_id")
    private Integer calibration_schedule_id;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "created_by")
    private String created_by;

    @Column(name = "current_calibration_date")
    private String current_calibration_date;

    @Column(name = "additional_information")
    private String additional_information;

    @Column(name = "status_updated_at")
    private Date status_updated_at;

    @Column(name = "submitted_by")
    private String submitted_by;

    @Column(name = "submitted_at")
    private Date submitted_at;

    @Column(name = "acknowleged_by")
    private String acknowleged_by;

    @Column(name = "acknowleged_at")
    private Date acknowleged_at;

    @Column(name = "rejected_by")
    private String rejected_by;

    @Column(name = "rejected_at")
    private Date rejected_at;

    @Column(name = "next_calibration_date")
    private Date next_calibration_date;

    @Column(name = "task_submission_file_base_url")
    private String task_submission_file_base_url;

    @Column(name = "inspected_detail_type")
    private String inspected_detail_type;

    @Column(name = "submit_remark")
    private String submit_remark;

    @Column(name = "accept_remark")
    private String accept_remark;

    @Column(name = "reject_remark")
    private String reject_remark;

    @Column(name = "actual_calibration_date")
    private Date actual_calibration_date;

    @Column(name = "file_upload_url")
    private String file_upload_url;

    @Column(name = "update_remainder_1")
    private Integer update_remainder_1;

    @Column(name = "update_remainder_2")
    private Integer update_remainder_2;

    @Column(name = "update_remainder_3")
    private Integer update_remainder_3;

    @Column(name = "update_upcoming_remainder")
    private Integer update_upcoming_remainder;

    @Column(name = "update_approve_remainder")
    private Date update_approve_remainder;

    @Column(name = "update_due_remainder_frequency")
    private Date update_due_remainder_frequency;
}
