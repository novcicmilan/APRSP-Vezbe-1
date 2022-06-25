package cryptoconversion.helper;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import cryptoconversion.dto.CryptoWalletDto;

@FeignClient("crypto-wallet")
public interface CryptoWalletProxy {
	
	@GetMapping("/crypto-wallet/{id}")
	CryptoWalletDto getById(@PathVariable Long id);

	@PutMapping("/crypto-wallet/{id}/from/{from}/to/{to}/quantity/{quantity}/total/{total}")
	public void exchange(@PathVariable Long id, @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity, @PathVariable BigDecimal total);
}
