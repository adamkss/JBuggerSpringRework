package com.adam.kiss.jbugger.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserDtoIn {
   private String name;
   private String phoneNumber;
   private String email;
   private String password;
   private String role;
}
