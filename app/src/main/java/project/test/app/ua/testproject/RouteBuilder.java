package project.test.app.ua.testproject;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

import project.test.app.ua.testproject.models.Location;

import static project.test.app.ua.testproject.help.Constants.CONST_MAP_API_GEOCODE_STATUS_OK;
import static project.test.app.ua.testproject.help.Constants.CONST_MAP_API_ROUTE_URL;

/**
 * Created by antonina on 31.07.15.
 */
public class RouteBuilder extends AsyncHttpClient {
    private Gson gson;
    private PathJSONParser parser;

    private ArrayList<ArrayList<LatLng>> routes = new ArrayList<>();


    public void getRoutes(ArrayList<Location> locations) {
        for (int i = 0; i < locations.size(); i++) {
            getRoute(locations.get(i));
        }
    }

    public ArrayList<ArrayList<LatLng>> getRoute(final Location loc) {
        if (loc.getDestinationLocation() != null || loc.getDepartureLocation() != null) {
            get(getGeocodeMapUrl(loc), new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    gson = new Gson();
                    parser = new PathJSONParser();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    String status = gson.fromJson(new String(response), JsonObject.class)
                            .get("status").getAsString();
                    if (status.equals(CONST_MAP_API_GEOCODE_STATUS_OK)) {
                        JsonObject object = gson.fromJson(new String(response), JsonObject.class);
                        routes = parser.parse(object);

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    String d = "hhj";
                }

                @Override
                public void onRetry(int retryNo) {

                }

                @Override
                public void onFinish() {
                    loc.setRoutes(routes);
                    MainActivity.drawRoutes(loc);
                }
            });
        }
        return  routes;
    }

    private String getGeocodeMapUrl(Location orderGeocode) {
        return CONST_MAP_API_ROUTE_URL
                + orderGeocode.getDepartureLocation().latitude + ","
                + orderGeocode.getDepartureLocation().longitude + "&destination="
                + orderGeocode.getDestinationLocation().latitude + ","
                + orderGeocode.getDestinationLocation().longitude;
    }
}
