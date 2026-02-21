package com.moniepointmerchant.merchantapi.service;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CsvImportService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void importAllCSVFiles() throws Exception {
    Path folder = Paths.get(System.getProperty("user.dir"), "data");

    Files.list(folder)
      .filter(path -> path.toString().endsWith(".csv"))
      .forEach(this::importCsvFile);
  }

  private void importCsvFile(Path path){

    try (BufferedReader reader = Files.newBufferedReader(path)) {

      String line;
      reader.readLine(); // skip header

      while ((line = reader.readLine()) != null){
        String[] fields = line.split(",");

        try {
          String eventId = fields[0]; // FIXED

          String merchantId = fields[1];

          Timestamp timestamp = parseTimestamp(fields[2]);

          String product = fields[3];
          String eventType = fields[4];

          BigDecimal amount = parseAmount(fields[5]);

          String status = fields[6];
          String channel = fields[7];
          String region = fields[8];
          String tier = fields[9];

          jdbcTemplate.update("""
            INSERT IGNORE INTO merchant_activities
            (event_id, merchant_id, event_timestamp, product, event_type, amount, status, channel, region, merchant_tier)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
          """,
          eventId, merchantId, timestamp, product, eventType,
          amount, status, channel, region, tier);
        }
        catch (Exception e){
          log.error("Failed row: {}", line);
          log.error("Error: {}", e.getMessage());
        }
      }

    } catch (Exception e){
      log.error("File error: {}", e.getMessage());
    }
  }

  private BigDecimal parseAmount(String value) {

    if (value == null || value.isBlank())
      return null;

    if (value.equalsIgnoreCase("INVALID"))
      return null;
    try {
      return new BigDecimal(value);
    }
    catch (Exception e) {
      return null;
    }
  }

  private Timestamp parseTimestamp(String value) {

    if (value == null || value.isBlank())
      return null;

    try {
      return Timestamp.valueOf(value.replace("T", " "));
    }
    catch (Exception e) {
      return null;
    }
  }
}
