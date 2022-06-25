package cryptotrade.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

public class CurrencyExchangeDto {

private long id;
	
	private String from;
	
	private String to;
	
	private BigDecimal conversionMultiple;

	public long getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}
}
