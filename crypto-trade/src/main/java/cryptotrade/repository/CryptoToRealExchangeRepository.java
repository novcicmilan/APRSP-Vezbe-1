package cryptotrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cryptotrade.model.CryptoToRealExchange;

public interface CryptoToRealExchangeRepository extends JpaRepository<CryptoToRealExchange, Long> {

	CryptoToRealExchange findByExchangeFromAndExchangeTo(String from, String to);
}
