package cryptotrade.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cryptotrade.dto.BankAccountDto;
import cryptotrade.dto.CryptoWalletDto;
import cryptotrade.dto.CurrencyExchangeDto;
import cryptotrade.helper.BankAccountProxy;
import cryptotrade.helper.CryptoWalletProxy;
import cryptotrade.helper.CurrencyConversionProxy;
import cryptotrade.helper.CurrencyExchangeProxy;
import cryptotrade.model.CryptoToRealExchange;
import cryptotrade.repository.CryptoToRealExchangeRepository;

@Service
public class CryptoToRealExchangeService {

	@Autowired
	private CryptoToRealExchangeRepository repo;

	@Autowired
	private CryptoWalletProxy walletProxy;

	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;

	@Autowired
	private CurrencyConversionProxy currencyConversionProxy;

	@Autowired
	private BankAccountProxy bankProxy;

	public Object trade(String from, String to, BigDecimal quantity, Long sender, Long reciever) {

		double recieverTotal = 0;
		double senderTotal = 0;
		DecimalFormat df = new DecimalFormat("0.00000");

		if (from.toLowerCase().equals("btc") || from.toLowerCase().equals("eth") || from.toLowerCase().equals("ada")) {
			CryptoWalletDto wallet = walletProxy.getById(sender);

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
				System.out.println(e.getMessage());
			}

			senderTotal = senderTotal - quantity.doubleValue();

			if (to.toLowerCase().equals("eur") || to.toLowerCase().equals("usd")) {
				CryptoToRealExchange temp = repo.findByExchangeFromAndExchangeTo(from, to);

				recieverTotal = recieverTotal + quantity.multiply(temp.getMultiplier()).doubleValue();
			} else if (to.toLowerCase().equals("chf") || to.toLowerCase().equals("gbp")
					|| to.toLowerCase().equals("rsd")) {

				CryptoToRealExchange temp = repo.findByExchangeFromAndExchangeTo(from, "EUR");

				CurrencyExchangeDto ceTemp = currencyExchangeProxy.getExchange("EUR", to.toUpperCase());

				recieverTotal = recieverTotal + quantity.multiply(temp.getMultiplier()).multiply(ceTemp.getConversionMultiple()).doubleValue();
			}

			CryptoWalletDto walletFinal = walletProxy.updateOne(wallet.getId(), from, new BigDecimal(senderTotal).setScale(5, RoundingMode.HALF_UP));

			BankAccountDto bankFinal = bankProxy.updateOne(reciever, to, new BigDecimal(recieverTotal).setScale(5, RoundingMode.HALF_UP));

			return bankFinal;
		} else if (from.toLowerCase().equals("eur") || from.toLowerCase().equals("usd")) {
			BankAccountDto temp = bankProxy.getBankAccount(sender);

			try {
				switch (from.toLowerCase()) {
				case "eur":
					if (temp.getEur().compareTo(quantity) < 0) {
						throw new RuntimeException("Not enought!");
					}
					break;
				case "usd":
					if (temp.getUsd().compareTo(quantity) < 0) {
						throw new RuntimeException("Not enought!");
					}
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			senderTotal = senderTotal - quantity.doubleValue();

			CryptoToRealExchange cryptoExchange = repo.findByExchangeFromAndExchangeTo(from, to);

			recieverTotal = recieverTotal + quantity.multiply(cryptoExchange.getMultiplier()).doubleValue();

			CryptoWalletDto walletFinal = walletProxy.updateOne(reciever, to, new BigDecimal(recieverTotal));

			BankAccountDto bankFinal = bankProxy.updateOne(sender, from, new BigDecimal(senderTotal));

			return walletFinal;
		} else if (from.toLowerCase().equals("chf") || from.toLowerCase().equals("gbp")
				|| from.toLowerCase().equals("rsd")) {
			BankAccountDto temp = bankProxy.getBankAccount(sender);

			try {
				switch (from.toLowerCase()) {
				case "rsd":
					if (temp.getEur().compareTo(quantity) < 0) {
						throw new RuntimeException("Not enought!");
					}
					break;
				case "gbp":
					if (temp.getUsd().compareTo(quantity) < 0) {
						throw new RuntimeException("Not enought!");
					}
					break;
				case "chf":
					if (temp.getUsd().compareTo(quantity) < 0) {
						throw new RuntimeException("Not enought!");
					}
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			senderTotal = senderTotal - quantity.doubleValue();
			
			CryptoToRealExchange cryptoExchange = repo.findByExchangeFromAndExchangeTo("EUR", to.toUpperCase());

			CurrencyExchangeDto ceTemp = currencyExchangeProxy.getExchange(from.toUpperCase(), "EUR");
			
			recieverTotal = recieverTotal + quantity.multiply(cryptoExchange.getMultiplier()).multiply(ceTemp.getConversionMultiple()).doubleValue();
			
			CryptoWalletDto walletFinal = walletProxy.updateOne(reciever, to, new BigDecimal(recieverTotal));

			BankAccountDto bankFinal = bankProxy.updateOne(sender, from, new BigDecimal(senderTotal));

			return walletFinal;
		}
		return null;
	}

}
