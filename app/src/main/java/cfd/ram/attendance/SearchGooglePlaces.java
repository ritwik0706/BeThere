package cfd.ram.attendance;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

/**
 * Created by mansi on 4/2/18.
 */

public class SearchGooglePlaces extends AppCompatActivity {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_google_places);
        button = findViewById(R.id.currentLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchGooglePlaces.this, CurrentLocation.class);
                startActivity(intent);
            }
        });
    }

    /*
     * In this method, Start PlaceAutocomplete activity
     * PlaceAutocomplete activity provides a -
     * search box to search Google places
     */
    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(this, "first error", Toast.LENGTH_SHORT).show();
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "second error", Toast.LENGTH_SHORT).show();
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                ((TextView) findViewById(R.id.searched_address))
                        .setText(place.getName()+",\n"+
                                place.getAddress() +"\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
