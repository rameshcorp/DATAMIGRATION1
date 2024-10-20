package com.project.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // This method performs the update
    @Transactional
    public void updateEquipmentTypeIds() {
        String sql = "UPDATE vms_equipment_parameter vep " +
                "JOIN equipment2 eq2 " +
                "ON vep.eqid = eq2.equipment_id " +
                "SET vep.equipment_type_id = eq2.equipment_type_id " +
                "WHERE vep.IsActive = 1";

        int rowsAffected = jdbcTemplate.update(sql);
        System.out.println("Rows affected: " + rowsAffected);
    }
}
