/* Gracenote Android Music SDK Sample Application
 *
 * Copyright (C) 2010 Gracenote, Inc. All Rights Reserved.
 */
package com.customer.example;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class ViewClass extends LinearLayout implements Checkable 
{ 
  
	public ViewClass(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	// Add your constructors 
     public boolean isChecked() { 
             // TODO Auto-generated method stub 
             return this.checked; 
     } 
     public void setChecked(boolean checked) { 
             // TODO Auto-generated method stub 
             getCheckBox().setChecked(checked); 
             this.checked = checked; 
             refreshDrawableState(); 
             //System.out.println("checked ....");
            // Append your event handler here 
     } 
     public void toggle() { 
             // TODO Auto-generated method stub 
             //getCheckBox().setChecked(!checked); 
             setChecked(!checked); 
     } 
     private CheckBox getCheckBox() 
     { 
             if (checkbox == null) 
             { 
                     checkbox = (CheckBox) findViewById(android.R.id.checkbox); 
//                   checkbox.setFocusable(false); 
//                   checkbox.setClickable(false);                 // The check box should decline the click event, hence the list item could be clicked. 
             } 
             return checkbox; 
     } 
     private CheckBox checkbox; 
     private boolean checked; 
} 
