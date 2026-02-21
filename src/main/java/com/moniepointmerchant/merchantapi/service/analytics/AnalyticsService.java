package com.moniepointmerchant.merchantapi.service.analytics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.moniepointmerchant.merchantapi.repository.MerchantActivityRepository;
import com.moniepointmerchant.merchantapi.response.FailureRateResponse;
import com.moniepointmerchant.merchantapi.response.TopMerchantResponse;
import com.moniepointmerchant.merchantapi.service.analytics.projections.FailureRateProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.KycFunnelProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.MonthlyActiveMerchantProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.ProductAdoptionProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.TopMerchantProjection;

@Service
public class AnalyticsService implements IAnalyticsService{
  @Autowired 
  private MerchantActivityRepository merchantActivityRepository;

  @Override
  public TopMerchantResponse getTopMerchant() {
    TopMerchantProjection result = merchantActivityRepository
          .findTopMerchant(PageRequest.of(0, 1))
          .stream()
          .findFirst()
          .orElseThrow(() -> new RuntimeException("No data found"));

    return new TopMerchantResponse(
                result.getMerchantId(),
                result.getTotalVolume()
    );
  }

  @Override
  public Map<String, Long> getMonthlyActiveMerchants() {
    List<MonthlyActiveMerchantProjection> results =
      merchantActivityRepository.getMonthlyActiveMerchants();

    Map<String, Long> response = new LinkedHashMap<>();

    for (MonthlyActiveMerchantProjection row : results) {
      response.put(row.getYearMonth(), row.getMerchantCount());
    }

    return response;
  }

  @Override
  public Map<String, Long> getProductAdoption() {
    List<ProductAdoptionProjection> results =
      merchantActivityRepository.getProductAdoption();

    Map<String, Long> response = new LinkedHashMap<>();

    for (ProductAdoptionProjection row : results) {
      response.put(row.getProduct(), row.getMerchantCount());
    }

    return response;
  }

  @Override
  public Map<String, Long> getKycFunnel() {
    KycFunnelProjection result = merchantActivityRepository.getKycFunnel();

    Map<String, Long> response = new LinkedHashMap<>();

    response.put("documents_submitted", result.getDocumentsSubmitted());
    response.put("verifications_completed", result.getVerificationsCompleted());
    response.put("tier_upgrades", result.getTierUpgrades());

    return response;
  }

  @Override
  public List<FailureRateResponse> getFailureRates() {
    List<FailureRateProjection> results = merchantActivityRepository.getFailureRates();

    List<FailureRateResponse> response = new ArrayList<>();

    for (FailureRateProjection row : results) {
      double roundedRate = Math.round(row.getFailureRate() * 10.0) / 10.0;
      response.add(new FailureRateResponse(
        row.getProduct(),
        roundedRate
      ));
    }

    return response;
  }

}
