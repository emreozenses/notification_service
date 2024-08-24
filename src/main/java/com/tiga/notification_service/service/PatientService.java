package com.tiga.notification_service.service;

import com.tiga.notification_service.dto.PatientQueryResponse;
import com.tiga.notification_service.entity.Patient;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientService {

    List<Patient> findAll();

    Patient findById(long patientId);

    Patient save(Patient patient);

    Patient update(long patientId,Patient patient);

    Patient delete(long patientId);

    void deleteDuplicatePatient();
}
