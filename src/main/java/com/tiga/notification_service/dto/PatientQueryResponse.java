package com.tiga.notification_service.dto;

import java.time.LocalDate;

public record PatientQueryResponse(Long patientId, String firstName, String midName, String surname, String gender,
                                   String birthDate, Integer age , String email, String cellPhone, String address) {
}

