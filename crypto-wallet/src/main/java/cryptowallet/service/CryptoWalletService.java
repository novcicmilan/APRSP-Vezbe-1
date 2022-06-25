package cryptowallet.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cryptowallet.dto.BankAccountDto;
import cryptowallet.dto.CreateCryptoWalletDto;
import cryptowallet.helper.BankAccountProxy;
import cryptowallet.model.CryptoWallet;
import cryptowallet.repository.CryptoWalletRepository;

@Service
public class CryptoWalletService {

	@Autowired
	CryptoWalletRepository repo;

	@Autowired
	BankAccountProxy proxy;

	public CryptoWallet create (CreateCryptoWalletDto createDto) {
		try {
			BankAccountDto temp = proxy.getBankAccount(createDto.getBankAccountId());
			if(temp == null) {
				throw new Exception("Bank Account not found");
			}
			
			CryptoWallet create = new CryptoWallet();
			create.setAda(createDto.getADA().multiply(new BigDecimal(100)));
			create.setBtc(createDto.getBTC().multiply(new BigDecimal(100)));
			create.setEth(createDto.getETH().multiply(new BigDecimal(100)));
			create.setId(createDto.getId());
			create.setBankAccountId(createDto.getBankAccountId());
			create.setEmailAddress(createDto.getEmailAddress());
			
			return repo.save(create);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public CryptoWallet findById(Long id) {
		return repo.findById(id).orElseThrow(()->{
			throw new RuntimeException("Crypto wallet not found!");
		});
	}
	
	public void update(CryptoWallet wallet) {
		repo.save(wallet);
	}
	
	public CryptoWallet updateOne(Long id, String update, BigDecimal quantity) {
		CryptoWallet wallet = findById(id);
		
		switch(update.toLowerCase()) {
		case "btc":
			wallet.setBtc(wallet.getBtc().add(quantity));
			break;
		case "ada":
			wallet.setAda(wallet.getAda().add(quantity));
			break;
		case "eth":
			wallet.setEth(wallet.getEth().add(quantity));
			break;
		default: 
			break;
		}
		
		return repo.save(wallet);
	}

}
