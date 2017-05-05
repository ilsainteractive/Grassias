package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Pricing {
    private String amount;

    private String value_currency;

    private String status;

    private String value_cents;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getValue_currency() {
        return value_currency;
    }

    public void setValue_currency(String value_currency) {
        this.value_currency = value_currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue_cents() {
        return value_cents;
    }

    public void setValue_cents(String value_cents) {
        this.value_cents = value_cents;
    }

    @Override
    public String toString() {
        return "ClassPojo [amount = " + amount + ", value_currency = " + value_currency + ", status = " + status + ", value_cents = " + value_cents + "]";
    }
}
