package com.challenge1.module.client.impl;

import com.challenge1.module.client.LobClient;
import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.exception.LobClientException;
import com.lob.api.ApiClient;
import com.lob.api.ApiException;
import com.lob.api.auth.HttpBasicAuth;
import com.lob.api.client.UsAutocompletionsApi;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.client.model.UsAutocompletions;
import org.openapitools.client.model.UsAutocompletionsWritable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LobClientImpl implements LobClient {

  @Value("${lob.apikey}")
  private String apikey;
  private final ApiClient lobClient;
  private final UsAutocompletionsApi usAutocompletionsApi;

  public LobClientImpl(ApiClient lobClient, UsAutocompletionsApi usAutocompletionsApi) {
    this.lobClient = lobClient;
    this.usAutocompletionsApi = usAutocompletionsApi;
  }

  @Override
  public UsAutocompletions getAddressFromLob(AddressInputDto addressInputDto) {
    log.info("Requesting addresses list to lob client");

    HttpBasicAuth basicAuth = (HttpBasicAuth) lobClient.getAuthentication("basicAuth");
    basicAuth.setUsername(apikey);

    UsAutocompletionsWritable autoCompletionWritable = new UsAutocompletionsWritable();
    autoCompletionWritable.setAddressPrefix(addressInputDto.getAddressPrefix());
    autoCompletionWritable.setCity(addressInputDto.getCity());
    autoCompletionWritable.setState(addressInputDto.getState());
    autoCompletionWritable.setZipCode(addressInputDto.getZipcode());

    try {
      UsAutocompletions usAutocompletion = usAutocompletionsApi.autocomplete(autoCompletionWritable);
      log.info("Received response from lob client: {}", usAutocompletion);
      return usAutocompletion;
    } catch (ApiException e) {
      log.error("Error getting response from lob client. Error: {}", e.getMessage());
      throw new LobClientException("Receiving error response from Lob Client");
    }
  }
}
