package com.moniepointmerchant.merchantapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailureRateResponse {
  private String product;
  private Double failure_rate;
}
