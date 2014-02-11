package com.jsbs.android.foursquare;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

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
    public static final String  EXTRA_VENUE                          = "venue";

    
    //https://api.foursquare.com/v2/venues/search?client_id=CLIENT_ID&client_secret=CLIENT_SECRET &v=20130815&ll=40.7,-74&query=sushi
    
    private static String URL_VENUE_SEARCH                           = "https://api.foursquare.com/v2/venues/search";
    private static final String PARAM_CLIENT_ID                      = "?" + EXTRA_CLIENT_ID+"=";
    private static final String PARAM_CLIENT_SECRET                  = "&" + EXTRA_CLIENT_SECRET+"=";
    private static final String PARAM_VERSION                        = "&v=";
    private static final String PARAM_LAT_LNG                        = "&ll=";
    private static final String PARAM_QUERY                          = "&query=";
    private static final String TAG = null;
    
    private EditText            editTextVenue;
    private ListView            venueList;
    
    String clientId;
    String clientSecret;
    private String lat;
    private String lng;
    private String query;
    
    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_list_venues);
        
        initComponent( );
        
        lat      = getIntent( ).getExtras( ).getString( EXTRA_LATITUDE);
        lng      = getIntent( ).getExtras( ).getString( EXTRA_LONGITUDE);
        query    = getIntent( ).getExtras( ).getString( EXTRA_QUERY);
        Log.d( TAG, "EXTRA_QUERY: " +query);

        clientId        = getIntent( ).getExtras( ).getString( EXTRA_CLIENT_ID);
        clientSecret    = getIntent( ).getExtras( ).getString( EXTRA_CLIENT_SECRET);
        
        if( clientId == null || clientId.equals( "") || clientSecret == null || clientSecret.equals( "")) {
            Toast.makeText( VenueSearchActivity.this, getString( R.string.error_to_get_extra_data_client_foursquare), Toast.LENGTH_SHORT).show( );
        } else {
            
            if( existDataIntent( lat, lng)) {
                 
                initDataToComponent( query);
                setupDataByFoursquare( lat, lng, query);
            } else {
                Toast.makeText( VenueSearchActivity.this, getString( R.string.error_to_get_extra_data), Toast.LENGTH_SHORT).show( );
            }
        }
    }
    
    private void initDataToComponent(String query ){
        if(editTextVenue == null) {
            editTextVenue = ( EditText) findViewById( R.id.editTextVenue);
        }
        if(query!=null && !query.equals( "")) {
            editTextVenue.setText( query);
            editTextVenue.setTextIsSelectable( true);

            editTextVenue.setFocusable( true);
            editTextVenue.setFocusableInTouchMode( true);

        }
        editTextVenue.setOnEditorActionListener( new OnEditorActionListener( ){
            
            @Override
            public boolean onEditorAction( TextView v, int actionId, KeyEvent event){
                
                searchVenue( editTextVenue.getText( ).toString( ), lat, lng);
                
                return false;
            }
        });
    }

    /**
     * Inicializes the components to start performing Venue in Foursquare.
     * 
     * @param lat
     * @param lng
     * @param query
     */
    private void setupDataByFoursquare( String lat, String lng, String query){     
        searchVenue( query, lat, lng);
    }
    
    /**
     * Check data form Intent.
     * 
     * @param lat
     * @param lng
     * @return
     */
    private boolean existDataIntent( String lat, String lng){
        if( lat != null && lng != null) {
            return true;
        } else {
            return false;
        }
    }
    
    private void initComponent( ){
        editTextVenue = ( EditText) findViewById( R.id.editTextVenue);
        editTextVenue.addTextChangedListener(new TextWatcher( ){
            
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
                searchVenue( editTextVenue.getText( ).toString( ), lat, lng);    
            }
        });
        editTextVenue.setOnEditorActionListener( new OnEditorActionListener( ){
            
            @Override
            public boolean onEditorAction( TextView v, int actionId, KeyEvent event){
                
                searchVenue( v.getText( ).toString( ), lat, lng);              
                return false;
            }
        });
        
        venueList = ( ListView) findViewById( R.id.venueList);
    }
    
    /**
     * Search venue by Foursquare
     */
    protected void searchVenue( String textToSearch, String lat, String lng){

       /**
        *  NOTE :
 -         * https://github.com/ddewaele/AndroidFoursquareGoogleApiJavaClient
 -         * /blob/master/src/com/ecs/android/foursquare/FoursquareVenueList.java
 -         * http
 -         * ://stackoverflow.com/questions/16315854/how-to-get-foursquare-venues
 -         * -list-android
        */
        
        // sample Date version 
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        
        String version = dateFormat.format(cal.getTime());
        
        String url = URL_VENUE_SEARCH + PARAM_CLIENT_ID + clientId + PARAM_CLIENT_SECRET + clientSecret+PARAM_VERSION+version+PARAM_LAT_LNG+lat+","+lng;
        
        if(textToSearch!=null){
            url = url +PARAM_QUERY+textToSearch;
        }
        
        Log.d( TAG, "Search Venue URL: " + url);
        
        try {
            //URLEncoder.encode( url.toString( )
            Ion.with( this, url)
            .asString( ).setCallback( new FutureCallback< String>( ){
                @Override
                public void onCompleted( Exception e, String result){
                    if(e!=null) {
                        Log.d( TAG, "EXCEPTION: " + e.getMessage( ));
                        return;
                    }else if(result!=null) {
                        Log.d( TAG, "RESULT FOURSQUARE JSON: " + result);
                        JSONObject object = null;
                        try {
                            object = new JSONObject(result);
                            JSONObject fourSquareResponse = (JSONObject) object.get("response");
                            JSONArray venues = (JSONArray) fourSquareResponse.get("venues");
                            
                            for( int i = 0; i < venues.length( ); i++) {
                                JSONObject place = (JSONObject)venues.get(i);
                                Log.i( TAG, "PLACE: " + place.toString( ));
                                Log.i( TAG, "==============================================");
                            }
                            
//                            JSONObject place = (JSONObject)venues.get(0);
//                            Log.i( TAG, "Found venues " + place.toString( ));
                        } catch( JSONException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                       
                    }                   
                }
                
            });
        } catch( Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace( );
        }
        
    }
    
    @Override
    public void onBackPressed( ){
        super.onBackPressed( );
        setResult( Activity.RESULT_CANCELED);
    }
    
}
