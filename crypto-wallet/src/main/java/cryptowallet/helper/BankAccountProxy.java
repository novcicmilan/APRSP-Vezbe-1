package cryptowallet.helper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cryptowallet.dto.BankAccountDto;

@FeignClient(name = "bank-account")
public interface BankAccountProxy {

	@GetMapping("/bank-account/{email}")
	BankAccountDto getBankAccount(@PathVariable String email);
}
