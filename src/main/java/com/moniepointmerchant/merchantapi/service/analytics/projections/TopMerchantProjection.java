package com.moniepointmerchant.merchantapi.service.analytics.projections;

import java.math.BigDecimal;

public interface TopMerchantProjection {
  String getMerchantId();
  BigDecimal getTotalVolume();
}
