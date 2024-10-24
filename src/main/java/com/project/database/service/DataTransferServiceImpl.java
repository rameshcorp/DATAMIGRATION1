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

//            String sql = "UPDATE vms_equipment_parameter vep " +
//                    "JOIN equipment2 eq2 " +
//                    "ON TRIM(LEADING '0' FROM vep.eqid) = TRIM(LEADING '0' FROM eq2.equipment_id) " +
//                    "OR vep.eqid IN ('100521062099', '103020558642','110721091791','110721091792'," +
//                    "'110721091796','110721091797','1108211090227','120221101359','120221102278'," +
//                    "'120521106564','120524106503','130421030569','13100217002767','140621080288'," +
//                    "'140621080296','141221080367','141221080392','170921114118','170921114119'," +
//                    "'170921114120','171121119014','171121119015','171121119016','171221114070'," +
//                    "'171221114072','171221114074','180832013961','180832013964','180832013967') " + // Fixed misplaced single quotes
//                    "SET vep.equipment_type_id = eq2.equipment_type_id " +
//                    "WHERE vep.IsActive = 1";


            //            "ON CAST(vep.eqid AS UNSIGNED) = CAST(eq2.equipment_id AS UNSIGNED) " +
            //            "ON TRIM(LEADING '0' FROM vep.eqid) = eq2.equipment_id " +


            String sql = "UPDATE inspected_details ins " +
                    "JOIN equipments1 eq2 " +
                    "SET ins.equipment_type_id = eq2.equipment_type_id " +
                    "WHERE ins.eqid=eq2.equipment_id";

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

    public void insertInspectedDetailsSort(){
        try{

            //updating active and type
//            String sql = "UPDATE inspected_details " +
//                    "SET active = 1, type = 1 " +
//                    "WHERE feature_inspector IS NOT NULL ";
//                    "AND spec_range IS NOT NULL";


//            String sql = "WITH sorted_values AS ( " +
//                    "    SELECT eqid, ROW_NUMBER() OVER (ORDER BY eqid ASC) AS new_sort_number " +
//                    "    FROM inspected_details " +
//                    ") " +
//                    "UPDATE inspected_details " +
//                    "SET sort_number = sorted_values.new_sort_number " +
//                    "FROM sorted_values " +
//                    "WHERE inspected_details.eqid = sorted_values.eqid;";

//            String sql = "UPDATE inspected_details AS id " +
//                    "JOIN ( " +
//                    "    SELECT eqid, ROW_NUMBER() OVER ( ORDER BY eqid ASC) AS new_sort_number " +
////                    "SELECT eqid, inspected_details_id, " +
////                    "ROW_NUMBER() OVER (PARTITION BY eqid ORDER BY inspected_details_id ASC) AS new_sort_number" +
//                    "    FROM inspected_details " +
//                    ") AS sorted_values " +
//                    "ON id.eqid = sorted_values.eqid " +
//                    "SET id.sort_number = sorted_values.new_sort_number;";

            String sql = "UPDATE inspected_details AS id " +
                    "JOIN ( " +
                    "    SELECT inspected_details_id, eqid, " +
                    "           ROW_NUMBER() OVER (PARTITION BY eqid ORDER BY inspected_details_id ASC) AS new_sort_number " +
                    "    FROM inspected_details " +
                    ") AS sorted_values " +
                    "ON id.inspected_details_id = sorted_values.inspected_details_id " +
                    "SET id.sort_number = sorted_values.new_sort_number;";



            // DENSE_RANK() will provide 1,2,3
//            String sql = " SET @row_number = 0; "+
//            " SET @current_eqid = NULL;" +
//
//            " UPDATE inspected_details AS id " +
//            "JOIN ( " +
//                    "SELECT eqid, sub_id," +
//            "@row_number := IF(@current_eqid = eqid, @row_number + 1, 1) AS new_sort_number,"+
//            "@current_eqid := eqid"+
//            "FROM inspected_details"+
//            "ORDER BY eqid, sub_id"+
//") AS sorted_values"+
//            "ON id.sub_id = sorted_values.sub_id"+
//            "SET id.sort_number = sorted_values.new_sort_number";


            // First set the variables
//            jdbcTemplate.execute("SET @row_number = 0;");
//            jdbcTemplate.execute("SET @current_eqid = NULL;");
//
//// Then run the update query
//            String sql = "UPDATE inspected_details AS id " +
//                    "JOIN ( " +
//                    "    SELECT eqid, sub_id, " +
//                    "           @row_number := IF(@current_eqid = eqid, @row_number + 1, 1) AS new_sort_number, " +
//                    "           @current_eqid := eqid " +
//                    "    FROM inspected_details " +
//                    "    ORDER BY eqid, sub_id " +
//                    ") AS sorted_values " +
//                    "ON id.sub_id = sorted_values.sub_id " +
//                    "SET id.sort_number = sorted_values.new_sort_number;";
//
//            jdbcTemplate.execute(sql);

            // 1. Initialize variables (run these separately)
//            jdbcTemplate.execute("SET @row_number = 0;");
//            jdbcTemplate.execute("SET @current_eqid = NULL;");
//            jdbcTemplate.execute("CREATE TEMPORARY TABLE temp_sorted_values (eqid INT, sub_id INT, new_sort_number INT);");
//
//            String insertIntoTempTable =
//                    "INSERT INTO temp_sorted_values (eqid, sub_id, new_sort_number) " +
//                            "SELECT eqid, sub_id, ROW_NUMBER() OVER (PARTITION BY eqid ORDER BY sub_id) AS new_sort_number " +
//                            "FROM inspected_details " +
//                            "ORDER BY eqid, sub_id;";
//
//            jdbcTemplate.execute(insertIntoTempTable);


//            String insertIntoTempTable =
//                    "INSERT INTO temp_sorted_values (eqid, sub_id, new_sort_number) " +
//                            "SELECT eqid, sub_id, COUNT(*) AS new_sort_number " +
//                            "FROM ( " +
//                            "    SELECT eqid, sub_id, " +
//                            "           @row_number := IF(@current_eqid = eqid, @row_number + 1, 1) AS new_sort_number, " +
//                            "           @current_eqid := eqid " +
//                            "    FROM inspected_details, " +
//                            "         (SELECT @row_number := 0, @current_eqid := NULL) AS vars " +
//                            "    ORDER BY eqid, sub_id " +
//                            ") AS derived_table " +
//                            "GROUP BY eqid, sub_id;";
//
//            jdbcTemplate.execute(insertIntoTempTable);


//            jdbcTemplate.execute("SET @row_number = 0;");
//            jdbcTemplate.execute("SET @current_eqid = NULL;");

//            String insertIntoTempTable = "INSERT INTO temp_sorted_values (eqid, sub_id, new_sort_number) " +
//                    "SELECT eqid, sub_id, " +
//                    "       @row_number := IF(@current_eqid = eqid, @row_number + 1, 1) AS new_sort_number, " +
//                    "       @current_eqid := eqid " +
//                    "FROM inspected_details " +
//                    "ORDER BY eqid, sub_id;";
//            jdbcTemplate.execute(insertIntoTempTable);



// 2. Create a temporary table with calculated row numbers
//            String createTempTable = "CREATE TEMPORARY TABLE temp_sorted_values AS " +
//                    "SELECT eqid, sub_id, new_sort_number FROM (" +
//                    "    SELECT eqid, sub_id, " +
//                    "           @row_number := IF(@current_eqid = eqid, @row_number + 1, 1) AS new_sort_number, " +
//                    "           @current_eqid := eqid " +
//                    "    FROM inspected_details " +
//                    "    ORDER BY eqid, sub_id" +
//                    ") AS derived_table;";
//            jdbcTemplate.execute(createTempTable);


//            String createTempTable = "CREATE TEMPORARY TABLE temp_sorted_values AS " +
//                    "SELECT eqid, sub_id, " +
//                    "       @row_number := IF(@current_eqid = eqid, @row_number + 1, 1) AS new_sort_number, " +
//                    "       @current_eqid := eqid " +
//                    "FROM inspected_details " +
//                    "ORDER BY eqid, sub_id;";
//            jdbcTemplate.execute(createTempTable);

// 3. Perform the update by joining with the temporary table
//            String updateQuery = "UPDATE inspected_details AS id " +
//                    "JOIN temp_sorted_values AS sorted_values " +
//                    "ON id.sub_id = sorted_values.sub_id " +
//                    "SET id.sort_number = sorted_values.new_sort_number;";
//            jdbcTemplate.execute(updateQuery);

// 4. Optionally drop the temporary table (not strictly necessary since MySQL will drop it automatically after the session)
//            jdbcTemplate.execute("DROP TEMPORARY TABLE temp_sorted_values;");




            int rowsAffected = jdbcTemplate.update(sql);
            System.out.println("Rows affected: " + rowsAffected);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertCalibrationSerialNo(){
        try{
            //inserting equipment_serial_number
            String sql = "insert INTO calibration_schedules (equipment_serial_number) " +
                    "select equipment_serial_number from equipments1";

            int rowsAffected = jdbcTemplate.update(sql);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void updateCalibration(){
        try{
            // correct status and calibration_type
                    String sql = "UPDATE calibration_schedules " +
                    "SET status='NEW', calibration_type='Internal', next_calibration_date='2025-09-02', " +
                            "status_updated_at='2024-02-05 03:25:48', created_by='B7052'" +
                    "WHERE calibration_schedule_id>0";


//           String sql= "UPDATE calibration_schedules cs "+
//            "SET cs.calibration_schedules_logs_id = ( "+
//                    "SELECT csl.calibration_schedule_log_id "+
//           "FROM calibration_schedule_logs csl "+
//            "WHERE cs.schedule_id = csl.schedule_id)";


//            String sql = "UPDATE calibration_schedules cs " +
//                    "join calibration_schedule_logs csl "+
//                    "ON cs.schedule_id = csl.schedule_id" +
//            " SET cs.calibration_schedules_logs_id=csl.calibration_schedule_log_id";
//                    "WHERE calibration_schedule_id>0";

//            correct first_issue_date
//            String sql = "update calibration_schedules cas " +
//                    "JOIN equipments1 eq1 ON cas.equipment_serial_number = eq1.equipment_serial_number " +
//                    "Join vms_equipment veq ON eq1.equipment_id = veq.equipmentID "+
//                    "SET cas.first_issue_date= veq.issuedate";


                    int rowsAffected = jdbcTemplate.update(sql);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    public void insertCalibrationLog(){
        try{
            //inserting equipment_serial_number
            String sql = "insert INTO calibration_schedules_logs (calibration_schedule_id, status, created_by) " +
                    "select calibration_schedule_id, status, created_by from calibration_schedules";

            int rowsAffected = jdbcTemplate.update(sql);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

@Transactional
    public void insertequipmentpmpic(){
        try {
            String selectSql = "SELECT eqid, piclist FROM vms_equipment_pmpic WHERE piclist IS NOT NULL";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectSql);
            List<Object[]> batchArgs = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                String eqid = (String) row.get("eqid");
                String picList = (String) row.get("piclist");
                String[] piclists = picList.split(",");
                for (String piclist : piclists) {
                    String trimmedPiclist = piclist.trim();

                    batchArgs.add(new Object[]{trimmedPiclist, eqid});
//
                    String insertSql = "INSERT ignore new_equipment_pmpic (piclist, eqid) VALUES (?, ?) ";
//                            "ON DUPLICATE KEY UPDATE eqid = eqid ";

                    int[] batchUpdateResult = jdbcTemplate.batchUpdate(insertSql, batchArgs);
                    System.out.println("Total inserted rows: " + batchUpdateResult.length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting inspected_details", e);
        }
    }

}


