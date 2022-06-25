package cryptotrade.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cryptotrade.service.CryptoToRealExchangeService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CryptoTradeController {

	@Autowired
	private CryptoToRealExchangeService service;
	
	@GetMapping("/crypto-trade/from/{from}/to/{to}/sender/{sender}/reciever/{reciever}/quantity/{quantity}")
	@Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
	@RateLimiter(name = "default")
	@Bulkhead(name = "default")
	private Object trade(@PathVariable String from, @PathVariable String to, @PathVariable Long sender, @PathVariable Long reciever, @PathVariable BigDecimal quantity) {
		return service.trade(from, to, quantity, sender, reciever);
	}
}
