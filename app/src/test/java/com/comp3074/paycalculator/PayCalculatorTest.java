package com.comp3074.paycalculator;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;
public class PayCalculatorTest {
 private void check(String h,String r,String t,String pay,String over,String total,String tax) {
  PayCalculator.Result x=PayCalculator.calculate(new BigDecimal(h),new BigDecimal(r),new BigDecimal(t));
  assertEquals(0,new BigDecimal(pay).compareTo(x.pay)); assertEquals(0,new BigDecimal(over).compareTo(x.overtimePay)); assertEquals(0,new BigDecimal(total).compareTo(x.totalPay)); assertEquals(0,new BigDecimal(tax).compareTo(x.tax));
 }
 @Test public void under40(){check("35","20","0.20","700","0","700","140");}
 @Test public void exactly40(){check("40","20","0.20","800","0","800","160");}
 @Test public void overtime(){check("45","20","0.20","800","150","950","160");}
 @Test public void fractional(){check("40.5","18.75","0.125","750","14.0625","764.0625","93.75");}
 @Test public void zero(){check("0","20","0","0","0","0","0");}
 @Test public void fullTax(){check("1","10","1","10","0","10","10");}
 @Test(expected=IllegalArgumentException.class) public void negative(){check("-1","20","0","0","0","0","0");}
 @Test(expected=IllegalArgumentException.class) public void invalidTax(){check("1","20","1.01","0","0","0","0");}
}
