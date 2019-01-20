package com.example.email.model;

import com.example.email.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
  private String messageId;
  private String parentMessageId;
  private String senderEmailId;
  private String body;
  private String attachmentUrl;
  private MessageType messageType;
}
