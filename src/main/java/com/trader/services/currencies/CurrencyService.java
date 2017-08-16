package com.trader.services.currencies;

import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
	private final Repository repository;

	CurrencyService(Repository repository) {
		this.repository = repository;
	}

	public Currency save(Currency currency) {
		return repository.save(currency);
	}

	public Iterable<Currency> findAll() {
		return repository.findAll();
	}
}
