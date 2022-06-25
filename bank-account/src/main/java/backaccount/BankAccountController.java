package backaccount;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@GetMapping("/bank-account/user/{email}")
	public BankAccount getBankAccount(@PathVariable String email) {
		return bankAccountRepository.findByEmailAddress(email);
	}
	
	@GetMapping("/bank-account/{id}")
	public BankAccount getBankAccount(@PathVariable Long id) {
		return bankAccountRepository.findById(id).get();
	}

	@PutMapping("/bank-account/{id}/from/{from}/to/{to}/amount/{amount}/total/{total}")
	public void exchangeCurrency(@PathVariable Long id, @PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal amount, @PathVariable BigDecimal total) {
		BankAccount bankAcc = bankAccountRepository.findById(id).get();
		
		switch (from.toUpperCase()) {
		case "USD":
			bankAcc.setUsd(bankAcc.getUsd().subtract(amount));
			break;
		case "GBP":
			bankAcc.setGbp(bankAcc.getGbp().subtract(amount));
			break;
		case "CHF":
			bankAcc.setChf(bankAcc.getChf().subtract(amount));
			break;
		case "EUR":
			bankAcc.setEur(bankAcc.getEur().subtract(amount));
			break;
		case "RSD":
			bankAcc.setRsd(bankAcc.getRsd().subtract(amount));
			break;
		default:
			break;
		}
			
		switch (to.toUpperCase()) {
		case "USD":
			bankAcc.setUsd(bankAcc.getUsd().add(total));
			break;
		case "GBP":
			bankAcc.setGbp(bankAcc.getGbp().add(total));
			break;
		case "CHF":
			bankAcc.setChf(bankAcc.getChf().add(total));
			break;
		case "EUR":
			bankAcc.setEur(bankAcc.getEur().add(total));
			break;
		case "RSD":
			bankAcc.setRsd(bankAcc.getRsd().add(total));
			break;
		}
				
		bankAccountRepository.save(bankAcc);
	}
	
	@PutMapping("/bank-account/{id}/update/{update}/quantity/{quantity}")
	public BankAccount updateOne(@PathVariable Long id, @PathVariable String update, @PathVariable BigDecimal quantity) {
		
		BankAccount bankAcc = bankAccountRepository.findById(id).get();
		
		switch (update.toUpperCase()) {
		case "USD":
			bankAcc.setUsd(bankAcc.getUsd().add(quantity));
			break;
		case "GBP":
			bankAcc.setGbp(bankAcc.getGbp().add(quantity));
			break;
		case "CHF":
			bankAcc.setChf(bankAcc.getChf().add(quantity));
			break;
		case "EUR":
			bankAcc.setEur(bankAcc.getEur().add(quantity));
			break;
		case "RSD":
			bankAcc.setRsd(bankAcc.getRsd().add(quantity));
			break;
		}
				
		return bankAccountRepository.save(bankAcc);
	}
}

	

