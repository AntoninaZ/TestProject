package project.test.app.ua.testproject;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

import project.test.app.ua.testproject.models.*;

import static project.test.app.ua.testproject.help.Constants.CONST_MAP_API_GEOCODE_STATUS_OK;
import static project.test.app.ua.testproject.help.Constants.CONST_MAP_API_GEOCODE_URL;

/**
 * Created by antonina on 30.07.15.
 */
public class GeocodeParser extends AsyncHttpClient {
    private Gson gson;
    private LatLng loc;
    private ArrayList<Location> locations = new ArrayList<>();

    public void parse(ArrayList<Order> orders) {
        for (int i  = 0; i < orders.size(); i++) {
            Location loc = new Location();
            loc.setDestinationLocation(new LatLng(0, 0));
            loc.setDepartureLocation(new LatLng(0, 0));
            locations.add(loc);
          //  loc.setDepartureLocation(getLocationFromAddress(orders.get(i).getDepartureAddress(), orders.size()));
          // loc.setDestinationLocation(getLocationFromAddress(orders.get(i).getDestinationAddress(), orders.size()));
            getLocationFromAddress(orders.get(i).getDepartureAddress(), i, orders.size(), 1);
            getLocationFromAddress(orders.get(i).getDestinationAddress(), i, orders.size(), 2);
           //
        }
    }

    private LatLng getLocationFromAddress(final Address address, final int index, final int size, final int code) {
        get(getGeocodeMapUrl(address), new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                gson = new Gson();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String status = gson.fromJson(new String(response), JsonObject.class).get("status").getAsString();
                if (status.equals(CONST_MAP_API_GEOCODE_STATUS_OK)){
                    JsonObject object = ((JsonArray)gson.fromJson(new String(response), JsonObject.class)
                        .get("results"))
                        .get(0).getAsJsonObject()
                        .get("geometry").getAsJsonObject()
                        .get("location").getAsJsonObject();
                    loc = new LatLng(object.get("lat").getAsDouble(), object.get("lng").getAsDouble());
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                loc = new LatLng(10, 10);
                // Toast.makeText(context, "Fail:(", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {

            }

            @Override
            public void onFinish() {
                if (code == 1) {
                   locations.get(index).setDepartureLocation(loc);
                }
                if (code == 2) {
                    locations.get(index).setDestinationLocation(loc);
                }
                boolean b = true;
                LatLng latLng = new LatLng(0.0, 0.0);
                for (int i = 0; i < size; i++) {
                    if (isLatLng(locations.get(i).getDepartureLocation()) || isLatLng(locations.get(i).getDestinationLocation())) {
                        b = false;
                    }
                }
                if (b) {
                    new RouteBuilder().getRoutes(locations);
                }
            }
        });
        return loc;
    }

    private boolean isLatLng(LatLng loc) {
        return loc.latitude == 0.0 || loc.longitude == 0.0;
    }

    private String getGeocodeMapUrl(Address address) {
        return CONST_MAP_API_GEOCODE_URL
                + address.getCountry() + "+"
                + address.getZipCode() + "+"
                + address.getStreet() + "+"
                + address.getHouseNumber() + "+"
                + address.getCity() + "+"
                + address.getCountryCode();
    }
}
