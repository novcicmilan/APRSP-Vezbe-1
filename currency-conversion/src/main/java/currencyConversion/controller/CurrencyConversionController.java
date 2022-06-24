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

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@Autowired
	private BankAccountProxy bankProxy;

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

		BankAccountDto bankAcc = bankProxy.getBankAccount(email);

		try {
			switch (from) {
			case "USD":
				if (bankAcc.getUsd().compareTo(quantity) < 0) {
					throw new Exception("Not enought usd");
				}
				break;
			case "GBP":
				if (bankAcc.getGbp().compareTo(quantity) < 0) {
					throw new Exception("Not enough gbp");
				}
				break;
			case "CHF":
				if (bankAcc.getChf().compareTo(quantity) < 0) {
					throw new Exception("Not enough chf");
				}
				break;
			case "EUR":
				if (bankAcc.getEur().compareTo(quantity) < 0) {
					throw new Exception("Not enough eur");
				}
				break;
			case "RSD":
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
		
		bankProxy.exchangeCurrency(bankAcc.getId(), from, to, quantity, quantity.multiply(temp.getConversionMultiple()));

		return new CurrencyConversion(temp.getId(), from, to, temp.getConversionMultiple(), quantity,
				quantity.multiply(temp.getConversionMultiple()), temp.getEnvironment() + "feign");
	}

}
