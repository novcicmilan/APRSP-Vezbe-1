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
import currencyConversion.helper.CurrencyExchangeProxy;
import currencyConversion.model.CurrencyConversion;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

		CurrencyConversion temp = response.getBody();

		return new CurrencyConversion(temp.getId(), from, to, temp.getConversionMultiple(), quantity,
				quantity.multiply(temp.getConversionMultiple()), temp.getEnvironment());
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}/user/{email}")
	public CurrencyConversion getConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity, @PathVariable String email) throws Exception {

		BankAccountDto bankAcc = proxy.getBankAccount(email);

		try {
			switch (from) {
			case "usd":
				if (bankAcc.getUsd().compareTo(quantity) < 0) {
					throw new Exception("Not enought usd");
				}
				break;
			case "gbp":
				if (bankAcc.getGbp().compareTo(quantity) < 0) {
					throw new Exception("Not enough gbp");
				}
				break;
			case "chf":
				if (bankAcc.getChf().compareTo(quantity) < 0) {
					throw new Exception("Not enough chf");
				}
				break;
			case "eur":
				if (bankAcc.getEur().compareTo(quantity) < 0) {
					throw new Exception("Not enough eur");
				}
				break;
			case "rsd":
				if (bankAcc.getRsd().compareTo(quantity) < 0) {
					throw new Exception("Not enough rsd");
				}
				break;
			default:
				throw new Exception("Unsuported currency");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		CurrencyConversion temp = proxy.getExchange(from, to);
		
		proxy.exchangeCurrency(bankAcc.getId(), from, to, quantity, temp.getTotalConversionAmount());

		return new CurrencyConversion(temp.getId(), from, to, temp.getConversionMultiple(), quantity,
				quantity.multiply(temp.getConversionMultiple()), temp.getEnvironment() + "feign");
	}

}
