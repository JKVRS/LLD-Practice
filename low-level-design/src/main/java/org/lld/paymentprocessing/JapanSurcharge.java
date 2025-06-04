package org.lld.paymentprocessing;

public class JapanSurcharge implements PricingStategy{
    @Override
    public Double applyPricing(Double amount) {
        return amount * 0.8;
    }
}
