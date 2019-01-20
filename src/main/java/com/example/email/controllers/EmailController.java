package com.example.email.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.email.model.Email;
import com.example.email.model.UserEmail;
import com.example.email.services.EmailService;

@RestController
public class EmailController {
  EmailService emailService;

  @Autowired
  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @RequestMapping(value = {"email/send"}, method = RequestMethod.POST)
  public boolean sendEmail(@RequestParam Boolean draftFlag,
      @RequestParam List<String> receiverEmailIds, @RequestBody Email email) {
    return emailService.sendEmail(email, draftFlag, receiverEmailIds);
  }

  @RequestMapping(value = {"user/email"}, method = RequestMethod.GET)
  public @ResponseBody List<UserEmail> getUserEmail(@RequestParam String emailId) {
    return emailService.getUserEmails(emailId);
  }

  @RequestMapping(value = {"email/get"}, method = RequestMethod.GET)
  public @ResponseBody Email getEmail(@RequestParam String messageId) {
    return emailService.getEmail(messageId);
  }

  @RequestMapping(value = {"email/thread"}, method = RequestMethod.GET)
  public @ResponseBody List<Email> getEmailThread(@RequestParam String messageId) {
    return emailService.getEmailThread(messageId);
  }

  @RequestMapping(value = {"email/delete"}, method = RequestMethod.GET)
  public boolean deleteEmail(@RequestParam String emailId, @RequestParam String messageId) {
    return emailService.deleteEmail(emailId, messageId);
  }

  @RequestMapping(value = {"email/draft"}, method = RequestMethod.POST)
  public boolean draftEmail(@RequestBody Email email, @RequestParam List<String> receiverEmailIds) {
    return emailService.draftEmail(email, receiverEmailIds);
  }
}
