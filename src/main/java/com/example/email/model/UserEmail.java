package com.example.email.model;

import com.example.email.enums.MessageFolderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmail {
  private String emailId;
  private String messageId;
  private MessageFolderType messageFolderType;
  private boolean isRead;
}
