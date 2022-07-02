package cryptoconversion.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cryptoconversion.dto.CryptoWalletDto;
import cryptoconversion.helper.CryptoExchangeProxy;
import cryptoconversion.helper.CryptoWalletProxy;
import cryptoconversion.model.CryptoConversion;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CryptoConversionController {

	@Autowired
	private CryptoExchangeProxy proxy;

	@Autowired
	private CryptoWalletProxy walletProxy;

	@GetMapping("/crypto-conversion/from/{from}/to/{to}/quantity/{quantity}/wallet/{email}")
	@RateLimiter(name = "default")
	public ResponseEntity<Object> getConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity, @PathVariable String email) {

		CryptoWalletDto wallet = walletProxy.getWallet(email);

		try {
			switch (from.toLowerCase()) {
			case "btc":
				if (wallet.getBtc().compareTo(quantity) < 0) {
					throw new RuntimeException("Not enough!");
				}
				break;
			case "eth":
				if (wallet.getEth().compareTo(quantity) < 0) {
					throw new RuntimeException("Not enough!");
				}
				break;
			case "ada":
				if (wallet.getAda().compareTo(quantity) < 0) {
					throw new RuntimeException("Not enought!");
				}
				break;
			default:
				throw new RuntimeException("Not supported crypto currency");

			}
		} catch (Exception e) {
				return ResponseEntity.ok("NOT SUPPOERTED CURRENCY");
		}

		CryptoConversion temp = proxy.getExchange(from, to);
		
		CryptoWalletDto walletExchanged = walletProxy.exchange(email, from, to, quantity, quantity.multiply(temp.getMultiple()));

		return ResponseEntity.ok(walletExchanged); 
				
	}
}
