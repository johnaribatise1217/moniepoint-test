package com.moniepointmerchant.merchantapi.service.analytics.projections;

public interface KycFunnelProjection {
  Long getDocumentsSubmitted();
  Long getVerificationsCompleted();
  Long getTierUpgrades();
}
