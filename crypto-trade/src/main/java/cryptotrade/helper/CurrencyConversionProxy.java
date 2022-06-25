package cryptotrade.helper;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("currency-conversion")
public interface CurrencyConversionProxy {

}
