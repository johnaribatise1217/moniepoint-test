package com.moniepointmerchant.merchantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MerchantapiApplication {
	static{
		com.moniepointmerchant.merchantapi.EnvLoader loader = 
		new com.moniepointmerchant.merchantapi.EnvLoader();
	}
	public static void main(String[] args) {
		SpringApplication.run(MerchantapiApplication.class, args);
	}

}
