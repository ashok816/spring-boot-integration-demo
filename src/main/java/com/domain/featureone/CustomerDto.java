package com.domain.featureone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Integer id;
    private String email;
    private String name;
    private String phoneNumber;
}
