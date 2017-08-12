package com.trader.currencies;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Currency {
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Length(min = 3, max = 25)
	private String name;

	@NotNull
	@Length(min = 1, max = 10)
	private String abbreviation;

	@NotNull
	@DecimalMin("0")
	private Integer confirmations;

	@NotNull
	private Double fee;
	private String baseAddress;

	protected Currency() {
		//
	}

	public Currency(String name, String abbreviation, Integer confirmations, Double fee, String baseAddress) {
		this.setName(name);
		this.setAbbreviation(abbreviation);
		this.setConfirmations(confirmations);
		this.setFee(fee);
		this.setBaseAddress(baseAddress);
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getBaseAddress() {
		return baseAddress;
	}

	public Integer getConfirmations() {
		return confirmations;
	}

	public Double getFee() {
		return fee;
	}

	public String getName() {
		return name;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setBaseAddress(String baseAddress) {
		this.baseAddress = baseAddress;
	}

	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public void setName(String name) {
		this.name = name;
	}
}
