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

	@PutMapping("/bank-account/{id}/from/{from}/to/{to}/amount/{amount}/total/{total}")
	public void exchangeCurrency(@PathVariable Long id, @PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal amount, @PathVariable BigDecimal total) {
		BankAccount bankAcc = bankAccountRepository.findById(id).get();
		
		switch (from) {
		case "usd":
			bankAcc.setUsd(bankAcc.getUsd().subtract(amount));
			break;
		case "gbp":
			bankAcc.setGbp(bankAcc.getGbp().subtract(amount));
			break;
		case "chf":
			bankAcc.setChf(bankAcc.getChf().subtract(amount));
			break;
		case "eur":
			bankAcc.setEur(bankAcc.getEur().subtract(amount));
			break;
		case "rsd":
			bankAcc.setRsd(bankAcc.getRsd().subtract(amount));
			break;
		default:
			break;
		}
			
		switch (to) {
		case "usd":
			bankAcc.setUsd(bankAcc.getUsd().add(total));
			break;
		case "gbp":
			bankAcc.setGbp(bankAcc.getGbp().add(total));
			break;
		case "chf":
			bankAcc.setChf(bankAcc.getChf().add(total));
			break;
		case "eur":
			bankAcc.setEur(bankAcc.getEur().add(total));
			break;
		case "rsd":
			bankAcc.setRsd(bankAcc.getRsd().add(total));
			break;
		}
				
		bankAccountRepository.save(bankAcc);
	}
}

	

