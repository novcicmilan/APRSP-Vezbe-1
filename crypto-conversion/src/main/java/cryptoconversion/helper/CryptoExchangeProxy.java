package cryptoconversion.helper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cryptoconversion.model.CryptoConversion;

@FeignClient("crypto-exchange")
public interface CryptoExchangeProxy {

	@GetMapping("/crypto-exchange/from/{from}/to/{to}")
	CryptoConversion getExchange(@PathVariable String from, @PathVariable String to);
}
