package com.moniepointmerchant.merchantapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moniepointmerchant.merchantapi.model.MerchantActivity;
import com.moniepointmerchant.merchantapi.service.analytics.projections.FailureRateProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.KycFunnelProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.MonthlyActiveMerchantProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.ProductAdoptionProjection;
import com.moniepointmerchant.merchantapi.service.analytics.projections.TopMerchantProjection;

@Repository
public interface MerchantActivityRepository extends JpaRepository<MerchantActivity, String> {

  @Query("""
        SELECT m.merchantId AS merchantId, SUM(m.amount) AS totalVolume
        FROM MerchantActivity m
        WHERE m.status = 'SUCCESS'
          AND m.amount IS NOT NULL
        GROUP BY m.merchantId
        ORDER BY SUM(m.amount) DESC
    """)
  List<TopMerchantProjection> findTopMerchant(Pageable pageable);

   @Query(value = """
        SELECT 
            DATE_FORMAT(event_timestamp, '%Y-%m') AS yearMonth,
            COUNT(DISTINCT merchant_id) AS merchantCount
        FROM merchant_activities
        WHERE status = 'SUCCESS'
          AND event_timestamp IS NOT NULL
        GROUP BY yearMonth
        ORDER BY yearMonth
    """, nativeQuery = true)
  List<MonthlyActiveMerchantProjection> getMonthlyActiveMerchants();

  @Query("""
      SELECT m.product AS product, COUNT(DISTINCT m.merchantId) AS merchantCount
      FROM MerchantActivity m
      WHERE m.status = 'SUCCESS'
      GROUP BY m.product
      ORDER BY COUNT(DISTINCT m.merchantId) DESC
  """)
  List<ProductAdoptionProjection> getProductAdoption();

  //my mental model 
  //How many merchants reached each step of onboarding?

  @Query(value = """
      SELECT
        COUNT(DISTINCT CASE WHEN event_type = 'DOCUMENT_SUBMITTED' THEN merchant_id END) AS documentsSubmitted,
        COUNT(DISTINCT CASE WHEN event_type = 'VERIFICATION_COMPLETED' THEN merchant_id END) AS verificationsCompleted,
        COUNT(DISTINCT CASE WHEN event_type = 'TIER_UPGRADE' THEN merchant_id END) AS tierUpgrades
      FROM merchant_activities
      WHERE status = 'SUCCESS'
        AND product = 'KYC'
  """, nativeQuery = true)
  KycFunnelProjection getKycFunnel();

  @Query(value = """
        SELECT 
          product AS product,
          (SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) * 100.0) /
          NULLIF(SUM(CASE WHEN status IN ('FAILED','SUCCESS') THEN 1 ELSE 0 END), 0)
          AS failureRate
        FROM merchant_activities
        WHERE status IN ('FAILED','SUCCESS')
        GROUP BY product
        ORDER BY failureRate DESC
    """, nativeQuery = true)
  List<FailureRateProjection> getFailureRates();
}
