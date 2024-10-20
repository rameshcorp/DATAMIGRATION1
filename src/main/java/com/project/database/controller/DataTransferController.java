package com.project.database.controller;

import com.project.database.service.DataTransferService;
import com.project.database.Dto.ResponseDTO; // Assuming you create a ResponseDTO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataTransferController {

    private final DataTransferService dataTransferService;

    public DataTransferController(DataTransferService dataTransferService) {
        this.dataTransferService = dataTransferService;
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
}
