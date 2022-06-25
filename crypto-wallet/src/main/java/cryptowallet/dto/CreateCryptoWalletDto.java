package cryptowallet.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class CreateCryptoWalletDto {
	
	@NotNull
	private Long id;
	
	private BigDecimal BTC;
	
	private BigDecimal ETH;
	
	private BigDecimal ADA;
	
	@NotNull
	private Long bankAccountId;
	
	@NotNull
	private String emailAddress;

	public Long getId() {
		return id;
	}

	public BigDecimal getBTC() {
		return BTC;
	}

	public BigDecimal getETH() {
		return ETH;
	}

	public BigDecimal getADA() {
		return ADA;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	
}
