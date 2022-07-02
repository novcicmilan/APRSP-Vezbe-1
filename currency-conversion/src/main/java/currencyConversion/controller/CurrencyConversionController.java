package currencyConversion.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import currencyConversion.dto.BankAccountDto;
import currencyConversion.helper.BankAccountProxy;
import currencyConversion.helper.CurrencyExchangeProxy;
import currencyConversion.model.CurrencyConversion;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@Autowired
	private BankAccountProxy bankProxy;


	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}/user/{email}")
	public ResponseEntity<Object> getConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity, @PathVariable String email) throws Exception {

		BankAccountDto bankAcc = bankProxy.getBankAccount(email);
		if(bankAcc == null) {
			return ResponseEntity.ok("BANK ACCOUNT NOT FOUND.");
		}

		try {
			switch (from.toUpperCase()) {
			case "USD":
				if (bankAcc.getUsd().compareTo(quantity) < 0) {
					throw new Exception("USD");
				}
				break;
			case "GBP":
				if (bankAcc.getGbp().compareTo(quantity) < 0) {
					throw new Exception("GBP");
				}
				break;
			case "CHF":
				if (bankAcc.getChf().compareTo(quantity) < 0) {
					throw new Exception("CHF");
				}
				break;
			case "EUR":
				if (bankAcc.getEur().compareTo(quantity) < 0) {
					throw new Exception("EUR");
				}
				break;
			case "RSD":
				if (bankAcc.getRsd().compareTo(quantity) < 0) {
					throw new Exception("RSD");
				}
				break;
			default:
				return ResponseEntity.ok("USUPPORTED CURRENCY");
			}
		} catch (Exception e) {
			return ResponseEntity.ok("NOT ENOUGH " + e.getMessage());
		}

		CurrencyConversion temp = proxy.getExchange(from, to);
		
		return ResponseEntity.ok(bankProxy.exchangeCurrency(bankAcc.getEmailAddress(), from, to, quantity, quantity.multiply(temp.getConversionMultiple())));
	}

}
