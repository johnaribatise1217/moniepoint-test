package com.moniepointmerchant.merchantapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.moniepointmerchant.merchantapi.service.CsvImportService;

@Component
public class CopyCSVFilesRunner implements CommandLineRunner{

  @Autowired
  private CsvImportService importService;

  @Override
  public void run(String... args) throws Exception {
    importService.importAllCSVFiles();
  }

}
