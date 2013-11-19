package com.wade12.dragdrop;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.textBitbuzz).setOnLongClickListener(longListen);
		findViewById(R.id.textMeteor).setOnLongClickListener(longListen);
		findViewById(R.id.textO2).setOnLongClickListener(longListen);
		findViewById(R.id.textVodafone).setOnLongClickListener(longListen);
		
		findViewById(R.id.textDropTarget).setOnDragListener(dropListener);
	} // end method onCreate
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} // end method onCreateOptionsMenu
	
	
	OnLongClickListener longListen = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View view) {
			
			TextView company = (TextView) view;
            Toast.makeText(MainActivity.this, "Text clicked: " + company.getText() , Toast.LENGTH_SHORT).show();
			
			ClipData data = ClipData.newPlainText("", "");
			// MyShadowBuilder shadowBuilder = new MyShadowBuilder(view);
			DragShadow dragShadow = new DragShadow(view);
			// View.DragShadowBuilder myShadowBuilder = new MyShadowBuilder(view);
			view.startDrag(data, dragShadow, view, 0);
			
			return false;
		} // end onLongClick
	}; // end OnLongClickListener
	
	
	private class DragShadow extends View.DragShadowBuilder {
		
		ColorDrawable greyBox;

		public DragShadow(View view) {
			super(view);
			greyBox = new ColorDrawable(Color.LTGRAY);
		} // end constructor


		@Override
		public void onDrawShadow(Canvas canvas) {
			greyBox.draw(canvas);
		} // end method onDrawShadow
		
		
		@Override
		public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
			// super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
			
			View view = getView();
			int height = (int) view.getHeight()/2;
			int width = (int) view.getWidth()/2;
			
			greyBox.setBounds(0, 0, width, height);
			
			shadowSize.set(width, height);
			shadowTouchPoint.set((int) width/2, (int) height/2);
			
		} // end method onProvideShadowMetrics
		
	} // end private Class DragShadow

	
	// OnDragListener dragListener = new OnDragListener()
	OnDragListener dropListener = new OnDragListener() {
            
		@Override
        public boolean onDrag(View view, DragEvent event) {
			int dragEvent = event.getAction();
            // TextView dropText = (TextView) view;
                    
            	switch(dragEvent) {
                	case DragEvent.ACTION_DRAG_ENTERED:
                		Log.i("Drag Event", "Entered");
                        //dropText.setTextColor(Color.GREEN);
                        break;
                                    
                    case DragEvent.ACTION_DRAG_EXITED:
                    	Log.i("Drag Event", "Exited");
                        //dropText.setTextColor(Color.RED);
                        break;
                                    
                    case DragEvent.ACTION_DROP:
                    	Log.i("Drag Event", "Dropped");
                        // TextView draggedText = (TextView) event.getLocalState();
                        // dropText.setText(draggedText.getText());
                    	// dropText.setText(dragged.getText());
                        TextView target = (TextView) view;
                        TextView dragged = (TextView) event.getLocalState();
                        target.setText(dragged.getText());
                        break;
                    } // end switch
                    
                    return true;
            } // end method onDrag
    }; // end DropListener
		
} // end Class MainActivity
