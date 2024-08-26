package com.tiga.notification_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GenericResponseDTO {

    private String message;
    private String status;
    @JsonProperty("status_code")
    private int statusCode;
    private Object data;
    @JsonProperty("error_data")
    private Object errorData;
    @JsonProperty("error_message")
    private Map<String, String> errorMessage;
}