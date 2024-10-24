package com.project.database.controller;

import com.project.database.service.DataTransferService;
import com.project.database.Dto.ResponseDTO; // Assuming you create a ResponseDTO
import com.project.database.service.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/equipment")
public class DataTransferController {

    private final DataTransferService dataTransferService;
    private final EquipmentService equipmentService;

    public DataTransferController(DataTransferService dataTransferService, EquipmentService equipmentService) {
        this.dataTransferService = dataTransferService;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/transfer-data")
    public ResponseEntity<ResponseDTO> transferData() {
        dataTransferService.transferUserData();

        // Create a response object
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Data transfer successful!");
        // You can also set other response data like counts here if needed

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-equipment-type")
//    public ResponseEntity<String> updateEquipmentType() {
    public ResponseEntity<ResponseDTO> updateEquipmentType() {
        dataTransferService.updateEquipmentTypeIds();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
//        equipmentService.updateEquipmentTypeIds();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/insert-inspected-feature")
    public ResponseEntity<ResponseDTO> insertInspectedDetails() {
        dataTransferService.insertInspectedFeature();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-inspected-spec")
    public ResponseEntity<ResponseDTO> insertInspectedSpec() {
        dataTransferService.insertInspectedSpecrange();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-inspected-spec1")
    public ResponseEntity<ResponseDTO> insertInspectedSpec1() {
        dataTransferService.insertInspectedDetails();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-equipment-spec1")
    public ResponseEntity<ResponseDTO> insertEquipmentSpec1() {
        dataTransferService.insertEquipmentDetails();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-inspected-spec2")
    public ResponseEntity<ResponseDTO> insertInspectedSpec2() {
        dataTransferService.insertInspectedDetailsSort();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-calibration-serialno")
    public ResponseEntity<ResponseDTO> insertCalibrationSerial() {
        dataTransferService.insertCalibrationSerialNo();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-calibration-active")
    public ResponseEntity<ResponseDTO> insertCalibrationUpdate() {
        dataTransferService.updateCalibration();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-calibration-log")
    public ResponseEntity<ResponseDTO> insertCalibrationlog() {
        dataTransferService.insertCalibrationLog();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-equipment-pmpic")
    public ResponseEntity<ResponseDTO> insertequipmentpmpic() {
        dataTransferService.insertequipmentpmpic();
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Equipment Type IDs updated successfully.");
        return ResponseEntity.ok(response);
    }
}
