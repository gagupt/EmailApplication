package com.example.email.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.email.dao.client.EmailClient;
import com.example.email.dao.client.UserClient;
import com.example.email.enums.MessageFolderType;
import com.example.email.model.Email;
import com.example.email.model.UserEmail;

@Service
public class EmailServiceImpl implements EmailService {

  private final UserClient userClient;
  private final EmailClient emailClient;

  @Autowired
  public EmailServiceImpl(UserClient userClient, EmailClient emailClient) {
    this.userClient = userClient;
    this.emailClient = emailClient;
  }

  @Override
  public boolean sendEmail(Email email, boolean draftFlag, List<String> receiverEmailIds) {
    if (!verifyIfemailsExist(receiverEmailIds)) {
      return false;
    }
    emailClient.saveEmail(email);

    emailClient.saveReceiverEmail(receiverEmailIds, email.getMessageId());

    UserEmail userEmail = new UserEmail();
    userEmail.setEmailId(email.getSenderEmailId());
    userEmail.setMessageId(email.getMessageId());
    if (draftFlag) {
      userEmail.setMessageFolderType(MessageFolderType.DRAFT);
    } else {
      userEmail.setMessageFolderType(MessageFolderType.SENT);
    }
    userEmail.setRead(true);
    emailClient.saveUserEmail(userEmail);

    if (!draftFlag) {
      userEmail.setMessageId(email.getMessageId());
      userEmail.setMessageFolderType(MessageFolderType.INBOX);
      userEmail.setRead(false);

      for (String receiverEmailId : receiverEmailIds) {
        userEmail.setEmailId(receiverEmailId);
        emailClient.saveUserEmail(userEmail);
      }
    }
    return true;
  }

  private boolean verifyIfemailsExist(List<String> receiverEmailIds) {
    if (receiverEmailIds == null) {
      return false;
    }
    for (String emailId : receiverEmailIds) {
      if (userClient.getUser(emailId) == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public List<UserEmail> getUserEmails(String emailId) {
    return emailClient.getUserEmails(emailId);
  }

  @Override
  public Email getEmail(String messageId) {
    return emailClient.getEmail(messageId);
  }

  @Override
  public List<Email> getEmailThread(String messageId) {
    List<Email> emails = new ArrayList<>();
    Email email;
    email = emailClient.getEmail(messageId);
    emails.add(email);
    while (email.getParentMessageId() != null) {
      email = emailClient.getEmail(email.getParentMessageId());
      emails.add(email);
    }
    return emails;
  }

  @Override
  public boolean deleteEmail(String emailId, String messageId) {
    UserEmail userEmail = emailClient.getUserEmail(emailId, messageId);
    userEmail.setMessageFolderType(MessageFolderType.TRASH);
    return emailClient.saveUserEmail(userEmail);
  }

  @Override
  public boolean draftEmail(Email email, List<String> receiverEmailIds) {
    return sendEmail(email, true, receiverEmailIds);
  }
}
