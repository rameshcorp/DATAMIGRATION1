package com.project.database.service;

import com.project.database.Entities.Contact;
import com.project.database.Entities.Name;
import com.project.database.Entities.User;
import com.project.database.repo.ContactRepository;
import com.project.database.repo.NameRepository;
import com.project.database.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataTransferServiceImpl implements DataTransferService {
//public class DataTransferServiceImpl {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final NameRepository nameRepository;

    public DataTransferServiceImpl(UserRepository userRepository,
                                   ContactRepository contactRepository,
                                   NameRepository nameRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.nameRepository = nameRepository;
    }

    @Transactional
    public void transferUserData() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            System.out.println("Processing user: " + user.getUserId());
            // Create and save Contact entity
            Contact contact = new Contact();
            contact.setPhoneNumber(user.getPhoneNumber());
            contact.setUser(user);
            contactRepository.save(contact);

            // Create and save Name entity
            Name name = new Name();
            name.setName(user.getFirstName() + " " + user.getLastName());
            name.setUser(user);
            nameRepository.save(name);
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // This method performs the update
    @Transactional
    public void updateEquipmentTypeIds() {
        try {
//    String invalidDateCheckSql = "SELECT COUNT(*) FROM vms_equipment_parameter WHERE IF(nextpmdate = '0000-00-00',NULL,nextpmdate) IS NULL";
//    int invalidCount = jdbcTemplate.queryForObject(invalidDateCheckSql, Integer.class);
//    System.out.println("Invalid nextpmdate count: " + invalidCount);

            //jdbcTemplate.update("UPDATE vms_equipment_parameter SET nextpmdate = NULL WHERE nextpmdate = '0000-00-00'");


//    String sqlUpdateNextPmDate = "UPDATE vms_equipment_parameter SET nextpmdate = NULL WHERE nextpmdate != '0000-00-00' AND nextpmdate IS NOT NULL";

//    int rowsAffectedNextPmDate = jdbcTemplate.update(sqlUpdateNextPmDate);
//    System.out.println("Rows affected in nextpmdate update: " + rowsAffectedNextPmDate); JOIN

            String sql = "UPDATE vms_equipment_parameter vep " +
                    "JOIN equipment2 eq2 " +
                    "ON TRIM(LEADING '0' FROM vep.eqid) = TRIM(LEADING '0' FROM eq2.equipment_id) " +
                    "OR vep.eqid IN ('100521062099', '103020558642','110721091791','110721091792'," +
                    "'110721091796','110721091797','1108211090227','120221101359','120221102278'," +
                    "'120521106564','120524106503','130421030569','13100217002767','140621080288'," +
                    "'140621080296','141221080367','141221080392','170921114118','170921114119'," +
                    "'170921114120','171121119014','171121119015','171121119016','171221114070'," +
                    "'171221114072','171221114074','180832013961','180832013964','180832013967') " + // Fixed misplaced single quotes
                    "SET vep.equipment_type_id = eq2.equipment_type_id " +
                    "WHERE vep.IsActive = 1";


            //            "ON CAST(vep.eqid AS UNSIGNED) = CAST(eq2.equipment_id AS UNSIGNED) " +
            //            "ON TRIM(LEADING '0' FROM vep.eqid) = eq2.equipment_id " +

            int rowsAffected = jdbcTemplate.update(sql);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // correct code to insert feature_inspector and eqid
    @Transactional
    public void insertInspectedFeature() {
        try {
            String selectSql = "SELECT eqid, parameterlist FROM vms_equipment_parameter WHERE parameterlist IS NOT NULL";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectSql);

            // Prepare a map to hold the last parameter for each eqid
//            Map<Integer, String> parameterMap = new HashMap<>();

            // Prepare a batch list for better performance
            List<Object[]> batchArgs = new ArrayList<>();

            for (Map<String, Object> row : rows) {
                String eqid = (String) row.get("eqid");
                String parameterList = (String) row.get("parameterlist");

                // Split parameterList by comma
                String[] parameters = parameterList.split(",");

                for (String parameter : parameters) {
                    String trimmedParameter = parameter.trim();
//                    parameterMap.put(eqid, trimmedParameter);
                    batchArgs.add(new Object[]{trimmedParameter, eqid});

                    // Prepare the INSERT query
                    String insertSql = "INSERT INTO inspected_details (feature_inspector, eqid) VALUES (?, ?) ";
//                            + "ON DUPLICATE KEY UPDATE feature_inspector = VALUES(feature_inspector)";

                    int[] batchUpdateResult = jdbcTemplate.batchUpdate(insertSql, batchArgs);
                    // Log or return the number of rows affected
                    System.out.println("Total inserted rows: " + batchUpdateResult.length);

                    // Batch update for better performance
                    // Update inspected_details table with each parameter for the corresponding eqid
//                    String updateSql = "UPDATE inspected_details SET feature_inspector = ? WHERE eqid = ?";
//                    jdbcTemplate.update(updateSql, trimmedParameter, eqid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting inspected_details", e);
        }
    }


    @Transactional
    public void insertInspectedSpecrange() {
        try {
            String selectSql = "SELECT speclist FROM vms_equipment_specrange WHERE speclist IS NOT NULL";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectSql);

            // Prepare a batch list for better performance
            List<Object[]> batchArgs = new ArrayList<>();

            for (Map<String, Object> row : rows) {
                String specList = (String) row.get("speclist");

                // Split parameterList by comma
                String[] specrange = specList.split(",");

                for (String spec : specrange) {
                    String trimmedSpec = spec.trim();
                    batchArgs.add(new Object[]{trimmedSpec});

                    // Prepare the INSERT query
                    String insertSql = "INSERT INTO inspected_details (spec_range) VALUES (?) ";
//                            + "ON DUPLICATE KEY UPDATE feature_inspector = VALUES(feature_inspector)";

                    int[] batchUpdateResult = jdbcTemplate.batchUpdate(insertSql, batchArgs);
                    // Log or return the number of rows affected
                    System.out.println("Total inserted rows: " + batchUpdateResult.length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting inspected_details", e);
        }
    }

    //    @Transactional
    public void insertInspectedDetails() {
        try {

            // Query to fetch eqid and parameterlist from vms_equipment_parameter
            String selectParameterSql = "SELECT eqid, parameterlist FROM vms_equipment_parameter WHERE parameterlist IS NOT NULL";

            // Query to fetch eqid and speclist from vms_equipment_specrange
            String selectSpecRangeSql = "SELECT eqid, speclist FROM vms_equipment_specrange WHERE speclist IS NOT NULL";

            // Fetch parameter data
            List<Map<String, Object>> parameterRows = jdbcTemplate.queryForList(selectParameterSql);

            // Fetch spec range data
            List<Map<String, Object>> specRangeRows = jdbcTemplate.queryForList(selectSpecRangeSql);

            // Create a map to store speclist values by eqid for faster access
            Map<String, String> specRangeMap = new HashMap<>();
            for (Map<String, Object> specRow : specRangeRows) {
                String eqid = (String) specRow.get("eqid");
                String speclist = (String) specRow.get("speclist");
                specRangeMap.put(eqid, speclist);
            }

            // Prepare a batch list for better performance
            List<Object[]> batchArgs = new ArrayList<>();

            // Loop through parameter data
            for (Map<String, Object> parameterRow : parameterRows) {
                String eqid1 = (String) parameterRow.get("eqid");
                String parameterList = (String) parameterRow.get("parameterlist");

                // Split parameterList by comma
                String[] parameters = parameterList.split(",");

                // Get the corresponding speclist from specRangeMap
                String speclist = specRangeMap.get(eqid1);
                String[] specs = (speclist != null) ? speclist.split(",") : new String[]{};

                // Ensure we have the same number of parameters and specs for consistency
                int maxEntries = Math.max(parameters.length, specs.length);

                // Loop through both parameters and specs
                for (int i = 0; i < maxEntries; i++) {
//                    for (int i = 0; i < parameters.length; i++) {
                    String trimmedParameter = (i < parameters.length) ? parameters[i].trim() : null;
                    String trimmedSpec = (i < specs.length) ? specs[i].trim() : null;

                    batchArgs.add(new Object[]{trimmedParameter, trimmedSpec, eqid1});
                }}
                    // Prepare the INSERT query
            //String insertSql = "INSERT INTO inspected_details (spec_range) VALUES (?) ";
                    String insertSql = "INSERT INTO inspected_details (feature_inspector, spec_range, eqid) "
                            + "VALUES (?, ?, ?) ";
//                            + "ON DUPLICATE KEY UPDATE "
//                            + "feature_inspector = VALUES(feature_inspector), "
//                            + "spec_range = VALUES(spec_range)";

            if (!batchArgs.isEmpty()) {
                // Perform batch insert for better performance
                int[] batchInsertResult = jdbcTemplate.batchUpdate(insertSql, batchArgs);

                // Log or return the number of rows affected
                System.out.println("Total inserts/updates: " + batchInsertResult.length);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error inserting inspected_details", e);
        }
    }

    public void insertEquipmentDetails(){
        try{
               String sql = "INSERT INTO equipments1 (equipment_id, equipment_type_id, department_id, created_date) " +
                "SELECT equipmentID, eqid, departmentid, createdDate FROM vms_equipment";

                int rowsAffected = jdbcTemplate.update(sql);
                System.out.println("Rows affected: " + rowsAffected);
        }
        catch (Exception e) {
                e.printStackTrace();
        }
    }

}












//@Transactional
//public void updateInspectedDetails() {
//    try {
//        String selectSql = "SELECT eqid, parameterlist FROM vms_equipment_parameter WHERE parameterlist IS NOT NULL";
//        List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectSql);
//
//        // Prepare a map to hold the last parameter for each eqid
//        Map<Integer, String> parameterMap = new HashMap<>();
//
//        for (Map<String, Object> row : rows) {
//            Integer eqid = (Integer) row.get("eqid");
//            String parameterList = (String) row.get("parameterlist");
//
//            // Split parameterList by comma
//            String[] parameters = parameterList.split(",");
//
//            // Assume the last parameter is the one you want to keep for each eqid
//            // (or modify this logic based on your needs)
//            for (String parameter : parameters) {
//                String trimmedParameter = parameter.trim();
//                parameterMap.put(eqid, trimmedParameter); // Keep only the last parameter for each eqid
//            }
//        }
//
//        // Prepare batch arguments
//        List<Object[]> batchArgs = new ArrayList<>();
//        String updateSql = "UPDATE inspected_details SET parameterlist = ? WHERE eqid = ?";
//
//        for (Map.Entry<Integer, String> entry : parameterMap.entrySet()) {
//            batchArgs.add(new Object[]{entry.getValue(), entry.getKey()});
//        }
//
//        // Execute batch update
//        if (!batchArgs.isEmpty()) {
//            int[] batchUpdateResult = jdbcTemplate.batchUpdate(updateSql, batchArgs);
//            System.out.println("Total updates: " + batchUpdateResult.length);
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        throw new RuntimeException("Error updating inspected_details", e);
//    }
//}

