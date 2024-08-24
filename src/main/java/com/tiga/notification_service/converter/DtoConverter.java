package com.tiga.notification_service.converter;

import com.tiga.notification_service.dto.PatientQueryResponse;
import com.tiga.notification_service.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

    public static PatientQueryResponse convertToPatientQueryResponse(Patient patient){
        return new PatientQueryResponse(patient.getPatientId(), patient.getFirstName(), patient.getMidName(), patient.getSurname(),patient.getGender(),patient.getBirthDate(), patient.getAge(),patient.getEmail(), patient.getCellPhone(),patient.getAddress());
    }

    public static List<PatientQueryResponse> convertToPatientQueryResponseList (List<Patient> patients){
        List<PatientQueryResponse> patientQueryResponseList = new ArrayList<>();
        patients.forEach(patient -> {
            patientQueryResponseList.add(convertToPatientQueryResponse(patient));
        });
        return patientQueryResponseList;
    }
}
