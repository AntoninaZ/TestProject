package project.test.app.ua.testproject;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import project.test.app.ua.testproject.models.*;

import java.util.ArrayList;

import static project.test.app.ua.testproject.help.Constants.CONST_API_URL;

/**
 * Created by antonina on 30.07.15.
 */
public class HttpClient extends AsyncHttpClient {

    private Context context;
    private Gson gson;
    private ArrayList<Order> ordersData = new ArrayList<>();

    public HttpClient(Context context) {
        this.context = context;
    }

    public ArrayList<Order> getData() {
        get(CONST_API_URL, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                gson = new Gson();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                JsonArray jsonArray = gson.fromJson(new String(response), JsonArray.class);
                for (JsonElement orderItem : jsonArray) {
                    ordersData.add(gson.fromJson(orderItem, Order.class));
                }

                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(context, "Fail:(", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {

            }

            @Override
            public void onFinish() {
                new GeocodeParser().parse(ordersData);
            }
        });
        return ordersData;
    }
}
