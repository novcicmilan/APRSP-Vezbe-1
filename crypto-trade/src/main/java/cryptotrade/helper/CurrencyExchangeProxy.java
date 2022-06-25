package cryptotrade.helper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cryptotrade.dto.CurrencyExchangeDto;

@FeignClient("currency-exchange")
public interface CurrencyExchangeProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	CurrencyExchangeDto getExchange(@PathVariable String from, @PathVariable String to);

}
