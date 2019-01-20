package com.example.email.dao.client;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.email.model.User;

@Service
public class UserClient {
  private final RestTemplate restTemplate;
  public static final String USER_SVC_URL = "http://localhost:9001";
  public static final String USER_PATH = "user/get";

  @Autowired
  public UserClient(RestTemplateBuilder restTemplBuilder) {
    this.restTemplate = restTemplBuilder.build();
  }

  public User getUser(String emailId) {
    User user = null;
    URI uri = UriComponentsBuilder.fromHttpUrl(USER_SVC_URL).path(USER_PATH)
        .queryParam("emailId", emailId).build().toUri();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> requestEntity = new HttpEntity<>(headers);
    try {
      ResponseEntity<User> response =
          restTemplate.exchange(uri, HttpMethod.GET, requestEntity, User.class);
      user = response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
    return user;
  }
}
