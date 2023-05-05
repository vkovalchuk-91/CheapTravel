package org.geekhub.kovalchuk.service;

import org.geekhub.kovalchuk.model.entity.Currency;
import org.geekhub.kovalchuk.repository.jpa.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public boolean isCurrencyTableEmpty() {
        return currencyRepository.count() == 0;
    }

    public void addMainCurrenciesToDb() {
        Currency usd = new Currency();
        usd.setCode("USD");
        usd.setCurrencyName("Долар США");
        currencyRepository.save(usd);

        Currency eur = new Currency();
        eur.setCode("EUR");
        eur.setCurrencyName("Євро");
        currencyRepository.save(eur);

        Currency uah = new Currency();
        uah.setCode("UAH");
        uah.setCurrencyName("Українська Гривня");
        currencyRepository.save(uah);
    }

    public Currency getCurrencyByCode(String code) {
        return currencyRepository.findCurrencyByCode(code);
    }
}
