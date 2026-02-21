package com.moniepointmerchant.merchantapi.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.moniepointmerchant.merchantapi.model.enums.Channel;
import com.moniepointmerchant.merchantapi.model.enums.EventType;
import com.moniepointmerchant.merchantapi.model.enums.MerchantTier;
import com.moniepointmerchant.merchantapi.model.enums.ProductType;
import com.moniepointmerchant.merchantapi.model.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "merchant_activities")
@AllArgsConstructor
public class MerchantActivity {

  @Id
  @Column(name = "event_id")
  private String eventId;

  @Column(name = "merchant_id")
  private String merchantId;

  @Column(name = "event_timestamp")
  private Timestamp eventTimestamp;

  @Column(name = "product")
  @Enumerated(EnumType.STRING)
  private ProductType product;

  @Column(name = "event_type")
  @Enumerated(EnumType.STRING)
  private EventType eventType;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "channel")
  @Enumerated(EnumType.STRING)
  private Channel channel;

  @Column(name = "region")
  private String region;

  @Column(name = "merchant_tier")
  @Enumerated(EnumType.STRING)
  private MerchantTier merchantTier;
}