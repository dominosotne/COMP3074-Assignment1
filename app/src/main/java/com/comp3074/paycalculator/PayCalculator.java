package com.comp3074.paycalculator;
import java.math.BigDecimal;
/** Exact decimal arithmetic; round only when presenting currency. */
public final class PayCalculator {
 private PayCalculator() {}
 public static final class Result {
  public final BigDecimal pay, overtimePay, totalPay, tax;
  Result(BigDecimal p, BigDecimal o, BigDecimal t) { pay=p; overtimePay=o; totalPay=p.add(o); tax=t; }
 }
 /** taxRate is a fraction: 0.20 means 20%. Tax applies only to regular pay. */
 public static Result calculate(BigDecimal hours, BigDecimal rate, BigDecimal taxRate) {
  if(hours.signum()<0 || rate.signum()<0 || taxRate.signum()<0 || taxRate.compareTo(BigDecimal.ONE)>0)
   throw new IllegalArgumentException("Hours and rate must be nonnegative; tax must be between 0 and 1.");
  BigDecimal forty=new BigDecimal("40");
  BigDecimal pay=hours.min(forty).multiply(rate);
  BigDecimal overtime=hours.subtract(forty).max(BigDecimal.ZERO).multiply(rate).multiply(new BigDecimal("1.5"));
  return new Result(pay, overtime, pay.multiply(taxRate));
 }
}
