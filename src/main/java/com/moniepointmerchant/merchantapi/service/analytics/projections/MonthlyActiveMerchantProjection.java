package com.moniepointmerchant.merchantapi.service.analytics.projections;

public interface MonthlyActiveMerchantProjection {
  String getYearMonth();
  Long getMerchantCount();
}
