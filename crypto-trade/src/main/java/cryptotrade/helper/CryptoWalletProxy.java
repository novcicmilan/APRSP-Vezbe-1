package cryptotrade.helper;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import cryptotrade.dto.CryptoWalletDto;

@FeignClient("crypto-wallet")
public interface CryptoWalletProxy {

	@GetMapping("/crypto-wallet/{id}")
	CryptoWalletDto getById(@PathVariable Long id);	
	
	@PutMapping("/crypto-wallet/{id}/update/{update}/quantity/{quantity}")
	CryptoWalletDto updateOne(@PathVariable Long id, @PathVariable String update, @PathVariable BigDecimal quantity);
}
