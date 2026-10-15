package com.comp3074.paycalculator;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import android.text.InputType;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends Activity {
 private EditText hours, rate, tax;
 private TextView regular, overtime, total, taxOutput;
 private boolean calculated;
 private int dp(int n) { return (int)(n*getResources().getDisplayMetrics().density); }
 private TextView text(String value, int size) {
  TextView t=new TextView(this); t.setText(value); t.setTextSize(size); t.setTextColor(Color.rgb(24,42,63)); t.setPadding(0,dp(8),0,dp(8)); return t;
 }
 private EditText input(LinearLayout box, String label, int id) {
  box.addView(text(label,16)); EditText e=new EditText(this); e.setId(id); e.setSingleLine(true);
  e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL); e.setHint("0");
  box.addView(e,new LinearLayout.LayoutParams(-1,dp(52))); return e;
 }
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(Color.rgb(245,248,252));
  root.setOnApplyWindowInsetsListener((v,insets)-> { v.setPadding(insets.getSystemWindowInsetLeft(),insets.getSystemWindowInsetTop(),insets.getSystemWindowInsetRight(),insets.getSystemWindowInsetBottom()); return insets; });
  Toolbar bar=new Toolbar(this); bar.setTitle("Pay Calculator"); root.addView(bar); setActionBar(bar);
  ScrollView scroll=new ScrollView(this); root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
  LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(dp(24),dp(8),dp(24),dp(24)); scroll.addView(box);
  box.addView(text("Weekly earnings",28)); box.addView(text("Overtime after 40 hours is paid at 1.5 times your hourly rate.",16));
  hours=input(box,"Hours worked",101); rate=input(box,"Hourly rate ($)",102); tax=input(box,"Tax rate (%) — enter 20 for 20%",103);
  Button calculate=new Button(this); calculate.setText("Calculate pay"); box.addView(calculate); calculate.setOnClickListener(v->calculate());
  box.addView(text("Pay summary",22));
  regular=text("Pay: —",18); overtime=text("Overtime pay: —",18); total=text("Total pay: —",22); taxOutput=text("Tax: —",18);
  box.addView(regular); box.addView(overtime); box.addView(total); box.addView(taxOutput);
  box.addView(text("Tax is calculated on regular pay only, as specified in Assignment 1. Total pay is before tax.",14));
  Button about=new Button(this); about.setText("About"); box.addView(about); about.setOnClickListener(v->startActivity(new Intent(this,AboutActivity.class)));
  setContentView(root);
  if(state!=null) { hours.setText(state.getString("hours","")); rate.setText(state.getString("rate","")); tax.setText(state.getString("tax","")); if(state.getBoolean("calculated")) calculate(); }
 }
 private BigDecimal read(EditText e, boolean percent) {
  String value=e.getText().toString().trim();
  try { if(value.isEmpty() || value.length()>16) throw new NumberFormatException();
   BigDecimal n=new BigDecimal(value); if(n.signum()<0 || (percent && n.compareTo(new BigDecimal("100"))>0)) throw new NumberFormatException(); return n;
  } catch(NumberFormatException ex) { e.setError(percent?"Enter a tax percentage from 0 to 100":"Enter a valid nonnegative number (up to 16 characters)"); return null; }
 }
 private String money(BigDecimal n) { return "$"+n.setScale(2,RoundingMode.HALF_UP).toPlainString(); }
 private void calculate() {
  BigDecimal h=read(hours,false), r=read(rate,false), t=read(tax,true);
  if(h==null || r==null || t==null) { calculated=false; regular.setText("Pay: —"); overtime.setText("Overtime pay: —"); total.setText("Total pay: —"); taxOutput.setText("Tax: —"); return; }
  PayCalculator.Result result=PayCalculator.calculate(h,r,t.movePointLeft(2));
  regular.setText("Pay: "+money(result.pay)); overtime.setText("Overtime pay: "+money(result.overtimePay)); total.setText("Total pay: "+money(result.totalPay)); taxOutput.setText("Tax: "+money(result.tax)); calculated=true;
 }
 @Override protected void onSaveInstanceState(Bundle state) { super.onSaveInstanceState(state); state.putString("hours",hours.getText().toString()); state.putString("rate",rate.getText().toString()); state.putString("tax",tax.getText().toString()); state.putBoolean("calculated",calculated); }
}
