package org.lld.paymentprocessing;

public class RegularPricing implements PricingStategy{
    @Override
    public Double applyPricing(Double amount) {
        return amount;
    }
}
