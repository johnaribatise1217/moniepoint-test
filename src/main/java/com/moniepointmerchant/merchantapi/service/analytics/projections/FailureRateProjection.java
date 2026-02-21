package com.moniepointmerchant.merchantapi.service.analytics.projections;

public interface FailureRateProjection {
  String getProduct();
  Double getFailureRate();
}
