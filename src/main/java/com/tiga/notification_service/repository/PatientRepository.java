package com.tiga.notification_service.repository;

import com.tiga.notification_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM patients WHERE id IN (SELECT id FROM (SELECT id,ROW_NUMBER() OVER(PARTITION BY patient_id,first_name,surname,birth_date ORDER BY id) AS row_num FROM patients) t WHERE t.row_num>1) ",nativeQuery = true)
    void deleteDuplicatePatient();

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM patients WHERE gender='MALE'AND notification_preferences='EMAIL'AND age>40 ",nativeQuery = true)
    List<Patient> sendProstateCancerScreeningEMAIL();
}
