package com.comp3074.paycalculator;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
public class AboutActivity extends Activity {
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
  root.setOnApplyWindowInsetsListener((v,i)-> { v.setPadding(i.getSystemWindowInsetLeft(),i.getSystemWindowInsetTop(),i.getSystemWindowInsetRight(),i.getSystemWindowInsetBottom()); return i; });
  Toolbar bar=new Toolbar(this); bar.setTitle("About"); root.addView(bar); setActionBar(bar); getActionBar().setDisplayHomeAsUpEnabled(true);
  TextView info=new TextView(this); int pad=(int)(24*getResources().getDisplayMetrics().density); info.setPadding(pad,pad,pad,pad); info.setTextSize(21);
  info.setText("Pay Calculator\nCOMP3074 · Assignment 1\n\n"+getString(R.string.student_name)+"\nStudent ID: "+getString(R.string.student_id));
  root.addView(info); setContentView(root);
 }
 @Override public boolean onOptionsItemSelected(MenuItem item) {
  // Finish this screen and reveal the existing MainActivity on the back stack.
  if(item.getItemId()==android.R.id.home) { finish(); return true; }
  return super.onOptionsItemSelected(item);
 }
}
