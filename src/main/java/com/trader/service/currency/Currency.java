package com.trader.service.currency;

import com.trader.service.twitter.Twitter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class Currency {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Twitter twitter;

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

    Currency(String name, String abbreviation, Integer confirmations, Double fee, String baseAddress) {
        this.setName(name);
        this.setAbbreviation(abbreviation);
        this.setConfirmations(confirmations);
        this.setFee(fee);
        this.setBaseAddress(baseAddress);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }*/

    public String getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation.toUpperCase();
    }
}
