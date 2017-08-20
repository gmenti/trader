package com.trader.service.twitter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TwitterRepository extends CrudRepository<Twitter, Long> {
    //
}
