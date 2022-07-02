package cryptotrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import cryptotrade.model.CryptoToRealExchange;

@SpringBootApplication
@EnableFeignClients
public class CryptoTradeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CryptoTradeApplication.class, args);
	}

}
