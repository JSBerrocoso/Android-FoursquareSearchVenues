package com.jsbs.android.foursquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity{
    
    public static final String CLIENT_ID        = "WEAKP5QWQQ1LBVW2MHK1Q2S5YTGTCKE1XTAJZYCIVXNUH0EY";
    public static final String CLIENT_SECRET    = "KSDVIV21N3UPJ4N2B1M3XUDNTJIHDCZLR3QCKGO5A0KLASCL";

    
    private TextView textViewPlace;
    protected String lat = "40.2219766";
    protected String lng = "-5.751487";
    protected String query;
    private EditText editTextPlace;
    private Button button;
    
    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_main);
        
        editTextPlace = ( EditText) findViewById( R.id.editTextPlace);        
        editTextPlace.addTextChangedListener(new TextWatcher( ){
            
            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count){
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after){
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void afterTextChanged( Editable s){
                if( !editTextPlace.getText( ).toString( ).equals( "")) {
                    button.setEnabled( true);
                } else {
                    button.setEnabled( false);
                } 
            }
        });
   
        
        button = ( Button) findViewById( R.id.button);
        button.setOnClickListener( new OnClickListener( ){
            
            @Override
            public void onClick( View v){
                
                Intent i = new Intent( MainActivity.this, VenueSearchActivity.class);
                i.putExtra( VenueSearchActivity.EXTRA_CLIENT_ID, CLIENT_ID);
                i.putExtra( VenueSearchActivity.EXTRA_CLIENT_SECRET, CLIENT_SECRET);
                i.putExtra( VenueSearchActivity.EXTRA_LATITUDE, lat);
                i.putExtra( VenueSearchActivity.EXTRA_LONGITUDE, lng);
                if( editTextPlace.getText( ).toString( ).trim( ) != null && !editTextPlace.getText( ).toString( ).trim( ).equals( "")) {
                    i.putExtra( VenueSearchActivity.EXTRA_QUERY, query);
                }
                
                startActivityForResult( i, VenueSearchActivity.REQUEST_CODE_FOURSQUARE_VENUE_SEARCH);
            }
        });
       
    }
    
    @Override
    protected void onResume( ){
        super.onResume( );
        
        if( !editTextPlace.getText( ).toString( ).equals( "")) {
            button.setEnabled( true);
        } else {
            button.setEnabled( false);
        }  
    }
    
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        super.onActivityResult( requestCode, resultCode, data);
        
        if( requestCode == VenueSearchActivity.REQUEST_CODE_FOURSQUARE_VENUE_SEARCH) {
            if( data != null) {
                String venue = data.getExtras( ).getString( VenueSearchActivity.EXTRA_VENUE);
                if( venue != null && !venue.equals( "")) {
                    textViewPlace.setText( venue);
                }
            }
            
        }
        
    }
}
