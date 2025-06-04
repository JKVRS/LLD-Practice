package org.lld.paymentprocessing;

public class PremiumPricing implements PricingStategy{
    @Override
    public Double applyPricing(Double amount) {
        return amount * 0.9;
    }
}
