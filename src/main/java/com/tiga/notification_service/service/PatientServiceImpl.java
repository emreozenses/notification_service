package com.tiga.notification_service.service;

import com.tiga.notification_service.entity.Patient;
import com.tiga.notification_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> findAll() {
        return null;
    }

    @Override
    public Patient findById(long patientId) {
        return null;
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(long patientId, Patient patient) {
        return null;
    }

    @Override
    public Patient delete(long patientId) {
        return null;
    }

    @Override
    public void deleteDuplicatePatient() {
        patientRepository.deleteDuplicatePatient();
    }

    @Override
    public List<Patient> findMaleAndNotificationEMAIL() {
        return patientRepository.findMaleAndNotificationEMAIL();
    }


}
