package com.moniepointmerchant.merchantapi.service.analytics;

import java.util.List;
import java.util.Map;

import com.moniepointmerchant.merchantapi.response.FailureRateResponse;
import com.moniepointmerchant.merchantapi.response.TopMerchantResponse;

public interface IAnalyticsService {
  TopMerchantResponse getTopMerchant();
  Map<String, Long> getMonthlyActiveMerchants();
  Map<String, Long> getProductAdoption();
  Map<String, Long> getKycFunnel();
  List<FailureRateResponse> getFailureRates();
}
