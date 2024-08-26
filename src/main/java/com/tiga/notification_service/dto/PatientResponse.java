package com.tiga.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private String firstName;
    private String surname;
    private String email;
}
