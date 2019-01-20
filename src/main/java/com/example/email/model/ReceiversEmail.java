package com.example.email.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiversEmail {
  private String emailId;
  private List<String> receiverEmailIds;
}
