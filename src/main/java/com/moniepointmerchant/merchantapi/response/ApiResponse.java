package com.moniepointmerchant.merchantapi.response;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
  private boolean success;
  private HttpStatusCode statusCode;
  private Object Response;
}
