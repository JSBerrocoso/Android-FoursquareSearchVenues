package com.jsbs.android.foursquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity{
    
    private TextView textViewPlace;
    protected String lat = "40.2219766";
    protected String lng = "-5.751487";
    protected String query;
    private EditText editTextPlace;
    
    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_main);
        
        editTextPlace = ( EditText) findViewById( R.id.editTextPlace);
        
        textViewPlace = ( TextView) findViewById( R.id.textViewPlace);
        textViewPlace.setOnClickListener( new OnClickListener( ){
            
            @Override
            public void onClick( View v){
                
                Intent i = new Intent( MainActivity.this, VenueSearchActivity.class);
                i.putExtra( VenueSearchActivity.EXTRA_LATITUDE, lat);
                i.putExtra( VenueSearchActivity.EXTRA_LONGITUDE, lng);
                i.putExtra( VenueSearchActivity.EXTRA_QUERY, query);
                
                startActivityForResult( i, VenueSearchActivity.REQUEST_CODE_FOURSQUARE_VENUE_SEARCH);
            }
        });
        
    }
    
    @Override
    protected void onResume( ){
        super.onResume( );
        
        if( !editTextPlace.getText( ).toString( ).equals( "")) {
            textViewPlace.setEnabled( true);
        } else {
            textViewPlace.setEnabled( false);
        }
        
    }
    
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        super.onActivityResult( requestCode, resultCode, data);
        
        if( requestCode == VenueSearchActivity.REQUEST_CODE_FOURSQUARE_VENUE_SEARCH) {
            
        }
        
    }
}
