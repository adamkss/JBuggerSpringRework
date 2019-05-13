package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Bug;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordOfUserDtoIn {
  private String password;
}
