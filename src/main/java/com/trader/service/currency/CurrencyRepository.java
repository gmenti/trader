package com.trader.service.currency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CurrencyRepository extends CrudRepository<Currency, Long> {
    //
}