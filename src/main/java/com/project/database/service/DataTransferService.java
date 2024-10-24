package com.project.database.service;


import org.springframework.stereotype.Service;

@Service
public interface DataTransferService {
    void transferUserData();

    void updateEquipmentTypeIds();
    void insertInspectedFeature();
    void insertInspectedSpecrange();
    void insertInspectedDetails();
    void insertEquipmentDetails();
    void insertInspectedDetailsSort();
    void insertCalibrationSerialNo();
    void updateCalibration();
    void insertCalibrationLog();
    void insertequipmentpmpic();
}
