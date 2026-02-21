package com.moniepointmerchant.merchantapi.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopMerchantResponse {
  private String merchant_id;
  private BigDecimal total_volume;
}
