package com.trader.services.currency;

import com.trader.entities.Currency;
import org.springframework.data.repository.CrudRepository;

interface Repository extends CrudRepository<Currency, Long> {
    //
}