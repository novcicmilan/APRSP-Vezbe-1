package cryptowallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cryptowallet.model.CryptoWallet;

public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Long> {
	CryptoWallet findByEmailAddress(String email);
}
