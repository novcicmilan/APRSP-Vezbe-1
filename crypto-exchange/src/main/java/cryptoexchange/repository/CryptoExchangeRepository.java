package cryptoexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cryptoexchange.model.CryptoExchange;

public interface CryptoExchangeRepository extends JpaRepository<CryptoExchange, Long>{
	CryptoExchange findByFromAndTo(String from, String to);
}
