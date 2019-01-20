package com.example.email.dao.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.email.model.Email;
import com.example.email.model.UserEmail;

@Service
public class EmailClient {
  private static final ParameterizedTypeReference<List<UserEmail>> LIST_TYPE_REF =
      new ParameterizedTypeReference<List<UserEmail>>() {};
  private static final ParameterizedTypeReference<List<String>> LIST_STR_TYPE_REF =
      new ParameterizedTypeReference<List<String>>() {};
  private final RestTemplate restTemplate;
  private static final String EMAIL_SVC_URL = "http://localhost:9001";
  private static final String EMAIL_PATH = "email/get";
  private static final String EMAIL_SAVE_PATH = "email/save";
  private static final String EMAIL_SAVE_USER = "user/email/save";
  private static final String EMAIL_USER_EMAIL = "user/email/get";
  private static final String EMAIL_USER_EMAILS = "user/emails/get";
  private static final String EMAIL_REC_GET = "email/get/receiver";
  private static final String EMAIL_REC_SAVE = "email/save/receiver";

  @Autowired
  public EmailClient(RestTemplateBuilder restTemplBuilder) {
    this.restTemplate = restTemplBuilder.build();
  }

  public Email getEmail(String messageId) {
    Email email = null;
    URI uri = UriComponentsBuilder.fromHttpUrl(EMAIL_SVC_URL).path(EMAIL_PATH)
        .queryParam("messageId", messageId).build().toUri();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> requestEntity = new HttpEntity<>(headers);
    try {
      ResponseEntity<Email> response =
          restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Email.class);
      email = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return email;
  }

  public boolean saveEmail(Email email) {
    HttpEntity<?> requestEntity = new HttpEntity<>(email, new HttpHeaders());
    Boolean success = false;
    URI uri = UriComponentsBuilder.fromHttpUrl(EMAIL_SVC_URL).path(EMAIL_SAVE_PATH).build().toUri();
    try {
      ResponseEntity<Boolean> response =
          restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Boolean.class);
      success = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return success != null && success;
  }

  public boolean saveUserEmail(UserEmail userEmail) {
    HttpEntity<?> requestEntity = new HttpEntity<>(userEmail, new HttpHeaders());
    Boolean success = false;
    URI uri = UriComponentsBuilder.fromHttpUrl(EMAIL_SVC_URL).path(EMAIL_SAVE_USER).build().toUri();
    try {
      ResponseEntity<Boolean> response =
          restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Boolean.class);
      success = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return success != null && success;
  }

  public List<UserEmail> getUserEmails(String emailId) {
    List<UserEmail> userEmails = new ArrayList<>();
    URI uri = UriComponentsBuilder.fromHttpUrl(EMAIL_SVC_URL).path(EMAIL_USER_EMAILS)
        .queryParam("emailId", emailId).build().toUri();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> requestEntity = new HttpEntity<>(headers);
    try {
      ResponseEntity<List<UserEmail>> response =
          restTemplate.exchange(uri, HttpMethod.GET, requestEntity, LIST_TYPE_REF);
      userEmails = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return userEmails;
  }

  public UserEmail getUserEmail(String emailId, String messageId) {
    UserEmail userEmail = null;
    URI uri = UriComponentsBuilder.fromHttpUrl(EMAIL_SVC_URL).path(EMAIL_USER_EMAIL)
        .queryParam("emailId", emailId).queryParam("messageId", messageId).build().toUri();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> requestEntity = new HttpEntity<>(headers);
    try {
      ResponseEntity<UserEmail> response =
          restTemplate.exchange(uri, HttpMethod.GET, requestEntity, UserEmail.class);
      userEmail = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return userEmail;
  }

  public boolean saveReceiverEmail(List<String> receiverEmailIds, String messageId) {
    HttpEntity<?> requestEntity = new HttpEntity<>(receiverEmailIds, new HttpHeaders());
    Boolean success = false;
    URI uri = UriComponentsBuilder.fromHttpUrl(EMAIL_SVC_URL).queryParam("messageId", messageId)
        .path(EMAIL_REC_SAVE).build().toUri();
    try {
      ResponseEntity<Boolean> response =
          restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Boolean.class);
      success = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return success != null && success;
  }

  public List<String> getReceiverEmail(String messageId) {
    List<String> userEmails = new ArrayList<>();
    URI uri = UriComponentsBuilder.fromHttpUrl(EMAIL_SVC_URL).path(EMAIL_REC_GET)
        .queryParam("messageId", messageId).build().toUri();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> requestEntity = new HttpEntity<>(headers);
    try {
      ResponseEntity<List<String>> response =
          restTemplate.exchange(uri, HttpMethod.GET, requestEntity, LIST_STR_TYPE_REF);
      userEmails = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return userEmails;
  }
}
