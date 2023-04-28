package org.geekhub.kovalchuk.repository;

import org.geekhub.kovalchuk.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findCurrencyByCode(String code);
}