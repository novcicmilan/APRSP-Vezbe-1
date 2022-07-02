package cryptowallet.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cryptowallet.dto.CreateCryptoWalletDto;
import cryptowallet.model.CryptoWallet;
import cryptowallet.service.CryptoWalletService;

@RestController
public class CryptoWalletController {
	
	@Autowired
	private CryptoWalletService service;

	@PostMapping("/crypto-wallet")
	public CryptoWallet create(@RequestBody CreateCryptoWalletDto createDto) {
		
		return service.create(createDto);
	}
	
	@GetMapping("/crypto-wallet/{email}")
	public CryptoWallet getWallet(@PathVariable String email) {
		return service.findByEmailAddress(email);
	}
	
	@PutMapping("/crypto-wallet/{email}/from/{from}/to/{to}/quantity/{quantity}/total/{total}")
	public CryptoWallet exchange(@PathVariable String email, @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity, @PathVariable BigDecimal total) {
		CryptoWallet wallet = service.findByEmailAddress(email);
		
		switch (from.toLowerCase()) {
		case "btc":
			wallet.setBtc(wallet.getBtc().subtract(quantity));
			break;
		case "eth":
			wallet.setEth(wallet.getEth().subtract(quantity));
			break;
		case "ada":
			wallet.setAda(wallet.getAda().subtract(quantity));
			break;
		default:
			break;
		}
		
		switch (to.toLowerCase()) {
		case "btc":
			wallet.setBtc(wallet.getBtc().add(total));
			break;
		case "eth":
			wallet.setEth(wallet.getEth().add(total));
			break;
		case "ada":
			wallet.setAda(wallet.getAda().add(total));
			break;
		default:
			break;
		}
		
		return service.update(wallet);
	}
	
	@PutMapping("/crypto-wallet/{email}/update/{update}/quantity/{quantity}")
	public CryptoWallet updateOne(@PathVariable String email, @PathVariable String update, @PathVariable BigDecimal quantity) {
		return service.updateOne(email, update, quantity);
	}
}
