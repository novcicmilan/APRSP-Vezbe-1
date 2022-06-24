package currencyConversion.helper;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import currencyConversion.dto.BankAccountDto;


//@FeignClient(name = "bank-account", url = "localhost:8200")
@FeignClient(name = "bank-account")
public interface BankAccountProxy {
	
	
	@GetMapping("/bank-account/user/{email}")
	BankAccountDto getBankAccount(@PathVariable String email);
	
	@PutMapping("/bank-account/{id}/from/{from}/to/{to}/amount/{amount}/total/{total}")
	public void exchangeCurrency(@PathVariable Long id, @PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal amount, @PathVariable BigDecimal total);

}
