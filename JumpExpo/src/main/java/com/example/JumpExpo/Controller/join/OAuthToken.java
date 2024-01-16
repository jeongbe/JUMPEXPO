package com.example.JumpExpo.Controller.join;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Data
public class OAuthToken {
  private String access_token;
  private String token_type;
  private String refresh_token;
  private int expires_in;
  private String scope;
  private int refresh_token_expires_in;



}
