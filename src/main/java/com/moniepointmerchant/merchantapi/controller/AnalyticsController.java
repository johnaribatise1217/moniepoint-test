package com.moniepointmerchant.merchantapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moniepointmerchant.merchantapi.response.ApiResponse;
import com.moniepointmerchant.merchantapi.response.FailureRateResponse;
import com.moniepointmerchant.merchantapi.response.TopMerchantResponse;
import com.moniepointmerchant.merchantapi.service.analytics.IAnalyticsService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
  @Autowired
  private IAnalyticsService analyticsService;

  @GetMapping("/top-merchant")
  public ResponseEntity<ApiResponse> GetTopMerchants(){
    TopMerchantResponse Response = analyticsService.getTopMerchant();
    return ResponseEntity.ok().body(new ApiResponse(true, HttpStatus.OK, Response));
  }

  @GetMapping("/monthly-active-merchants")
  public ResponseEntity<ApiResponse> GetMonthlyActiveMerchants(){
    Map<String, Long> Response = analyticsService.getMonthlyActiveMerchants();
    return ResponseEntity.ok().body(new ApiResponse(true, HttpStatus.OK, Response));
  }

  @GetMapping("/product-adoption")
  public ResponseEntity<ApiResponse> GetProductAdoption(){
    Map<String, Long> Response = analyticsService.getProductAdoption();
    return ResponseEntity.ok().body(new ApiResponse(true, HttpStatus.OK, Response));
  }

  @GetMapping("/kyc-funnel")
  public ResponseEntity<ApiResponse> GetKYCFunnel(){
    Map<String, Long> Response = analyticsService.getKycFunnel();
    return ResponseEntity.ok().body(new ApiResponse(true, HttpStatus.OK, Response));
  }

  @GetMapping("/failure-rates")
  public ResponseEntity<ApiResponse> GetFailureRates(){
    List<FailureRateResponse> Response = analyticsService.getFailureRates();
    return ResponseEntity.ok().body(new ApiResponse(true, HttpStatus.OK, Response));
  }
}
