package com.example.zegeju.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String error;
}
