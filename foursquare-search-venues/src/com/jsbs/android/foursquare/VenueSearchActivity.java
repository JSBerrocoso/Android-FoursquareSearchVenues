package com.jsbs.android.foursquare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;


/***
 * Activity showing an EditText to get a place through the Foursquare API. 
 *  Parameters for entry into the intent:<p>
 * 
 * <b>EXTRA_LATITUDE<br>
 * EXTRA_LONGITUDE<br>
 * EXTRA_QUERY</b><br>
 * 
 * @author jsberrocoso
 *
 */
public class VenueSearchActivity extends Activity{
    
    public static final String EXTRA_LATITUDE                       = "lat";
    public static final String EXTRA_LONGITUDE                      = "lng";
    
    public static final int    REQUEST_CODE_FOURSQUARE_VENUE_SEARCH = 200;
    public static final  String EXTRA_QUERY = "query";
    
    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_main);
 
        String lat =  getIntent( ).getExtras( ).getString( EXTRA_LATITUDE);
        String lng =  getIntent( ).getExtras( ).getString( EXTRA_LONGITUDE);
        String query =  getIntent( ).getExtras( ).getString( EXTRA_QUERY);
   
        if( !checkDataIntent( lat,lng)){
            setupDataByFoursquare( lat,lng, query);
        }else {
            Toast.makeText( VenueSearchActivity.this, getString( R.string.error_to_get_extra_data),Toast.LENGTH_SHORT).show();
        }   
    }

    /**
     * Inicializes the components to start performing Venue in Foursquare.
     * @param lat
     * @param lng
     * @param query
     */
    private void setupDataByFoursquare( String lat, String lng, String query){
        
        
        
    }

    /**
     * Check data form Intent.
     * @param lat
     * @param lng
     * @return
     */
    private boolean checkDataIntent( String lat, String lng){      
        if( lat != null && lng != null) {
            return true;
        } else {
            return false;
        }     
    }

   
    
    @Override
    public void onBackPressed( ){
        super.onBackPressed( );
        setResult( Activity.RESULT_CANCELED);
    }
    
}
