package cryptotrade.helper;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import cryptotrade.dto.BankAccountDto;

@FeignClient("bank-account")
public interface BankAccountProxy {
	
	@GetMapping("/bank-account/{id}")
	BankAccountDto getBankAccount(@PathVariable Long id);

	@PutMapping("/bank-account/{id}/update/{update}/quantity/{quantity}")
	BankAccountDto updateOne(@PathVariable Long id, @PathVariable String update, @PathVariable BigDecimal quantity);
}
