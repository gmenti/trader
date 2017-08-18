package com.trader.service.currencies;

import org.springframework.data.repository.CrudRepository;

interface CurrencyRepository extends CrudRepository<Currency, Long> {
    //
}