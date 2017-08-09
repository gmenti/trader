package com.trader.services.currency;

import org.springframework.data.repository.CrudRepository;

import com.trader.entities.Currency;

interface Repository extends CrudRepository<Currency, Long> {
	//
}