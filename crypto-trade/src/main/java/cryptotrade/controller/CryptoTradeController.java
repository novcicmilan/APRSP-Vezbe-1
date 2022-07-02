package cryptotrade.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	CryptoToRealExchangeService service;
	
	@GetMapping("/crypto-trade/from/{from}/to/{to}/user/{email}/quantity/{quantity}")
	private ResponseEntity<Object> trade(@PathVariable String from, @PathVariable String to, @PathVariable String email, @PathVariable BigDecimal quantity) {
		Object temp = service.trade(from, to, quantity, email);
		
		return ResponseEntity.ok(temp);
	}
}
