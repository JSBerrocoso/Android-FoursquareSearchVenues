package com.jsbs.android.foursquare;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/***
 * Activity showing an EditText to get a place through the <a
 * href="https://developer.foursquare.com/start/search">Foursquare API</a>.<br>
 * Parameters for entry into the intent:
 * <p>
 * 
 * <b>EXTRA_LATITUDE<br>
 * EXTRA_LONGITUDE<br>
 * EXTRA_QUERY</b><br>
 * 
 * @author jsberrocoso
 * 
 */
public class VenueSearchActivity extends Activity{
    
    public static final String  EXTRA_LATITUDE                       = "lat";
    public static final String  EXTRA_LONGITUDE                      = "lng";
    public static final String  EXTRA_CLIENT_ID                      = "client_id";
    public static final String  EXTRA_CLIENT_SECRET                  = "client_secret";
    
    public static final int     REQUEST_CODE_FOURSQUARE_VENUE_SEARCH = 200;
    public static final String  EXTRA_QUERY                          = "query";
    
    //https://api.foursquare.com/v2/venues/search?client_id=CLIENT_ID&client_secret=CLIENT_SECRET &v=20130815&ll=40.7,-74&query=sushi
    
    private static String URL_VENUE_SEARCH                     = "https://api.foursquare.com/v2/venues/search";
    private static final String PARAM_CLIENT_ID                      = "?" + EXTRA_CLIENT_ID;
    private static final String PARAM_CLIENT_SECRET                  = "&" + EXTRA_CLIENT_SECRET;
    private static final String PARAM_VERSION                        = "&v";
    private static final String PARAM_LAT_LNG                        = "&ll";
    private static final String PARAM_QUERY                          = "&query";
    
    private EditText            editTextVenue;
    private ListView            venueList;
    
    String clientId;
    String clientSecret;
    
    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_list_venues);
        
        initComponent( );
        
        String lat = getIntent( ).getExtras( ).getString( EXTRA_LATITUDE);
        String lng = getIntent( ).getExtras( ).getString( EXTRA_LONGITUDE);
        String query = getIntent( ).getExtras( ).getString( EXTRA_QUERY);
        
        clientId = getIntent( ).getExtras( ).getString( EXTRA_CLIENT_ID);
        clientSecret = getIntent( ).getExtras( ).getString( EXTRA_CLIENT_SECRET);
        
        if( clientId != null && !clientId.equals( "") && clientSecret != null && !clientSecret.equals( "")) {
            Toast.makeText( VenueSearchActivity.this, getString( R.string.error_to_get_extra_data), Toast.LENGTH_SHORT).show( );
        } else {
            
            if( !checkDataIntent( lat, lng)) {
                setupDataByFoursquare( lat, lng, query);
            } else {
                Toast.makeText( VenueSearchActivity.this, getString( R.string.error_to_get_extra_data), Toast.LENGTH_SHORT).show( );
            }
        }
    }
    
    /**
     * Inicializes the components to start performing Venue in Foursquare.
     * 
     * @param lat
     * @param lng
     * @param query
     */
    private void setupDataByFoursquare( String lat, String lng, String query){
        
        /**
         * NOTE :
         * https://github.com/ddewaele/AndroidFoursquareGoogleApiJavaClient
         * /blob/master/src/com/ecs/android/foursquare/FoursquareVenueList.java
         * http
         * ://stackoverflow.com/questions/16315854/how-to-get-foursquare-venues
         * -list-android
         */
        
//        String url =  URL_VENUE_SEARCH + PARAM_CLIENT_ID + clientId + PARAM_CLIENT_SECRET + clientSecret
        
        
    }
    
    /**
     * Check data form Intent.
     * 
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
    
    private void initComponent( ){
        editTextVenue = ( EditText) findViewById( R.id.editTextVenue);
        editTextVenue.setOnEditorActionListener( new OnEditorActionListener( ){
            
            @Override
            public boolean onEditorAction( TextView v, int actionId, KeyEvent event){
                String query = editTextVenue.getText( ).toString( ).trim( );
                if( !query.equals( "")) {
                    searchVenue( editTextVenue.getText( ).toString( ).trim( ));
                    return true;
                } else {
                    return false;
                }
            }
        });
        
        venueList = ( ListView) findViewById( R.id.editTextVenue);
    }
    
    /**
     * Realiza una busqueda a traves de Foursquare.
     */
    protected void searchVenue( String textToSearch){

//        Ion.with( VenueSearchActivity.this).load( "GET", URL_VENUE_SEARCH);
        
    }
    
    @Override
    public void onBackPressed( ){
        super.onBackPressed( );
        setResult( Activity.RESULT_CANCELED);
    }
    
}
