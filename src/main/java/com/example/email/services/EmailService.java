package com.example.email.services;

import java.util.List;
import com.example.email.model.Email;
import com.example.email.model.UserEmail;

public interface EmailService {
  boolean sendEmail(Email email, boolean draftFlag, List<String> receiverEmailIds);

  List<UserEmail> getUserEmails(String emailId);

  Email getEmail(String messageId);

  List<Email> getEmailThread(String messageId);

  boolean deleteEmail(String emailId, String messageId);

  boolean draftEmail(Email email, List<String> receiverEmailIds);
}
