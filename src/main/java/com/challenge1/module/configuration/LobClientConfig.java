package com.challenge1.module.configuration;

import com.lob.api.ApiClient;
import com.lob.api.client.UsAutocompletionsApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LobClientConfig {

  @Bean
  public ApiClient getApiClient () {
    return com.lob.api.Configuration.getDefaultApiClient();
  }

  @Bean
  public UsAutocompletionsApi getUsAutocompletionsApi () {
    return new UsAutocompletionsApi();
  }

}
