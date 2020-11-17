package com.example.tesis;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private int rutacc08idaC, rutacc08vueltaC;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    sendRequest();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendRequest();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin + "arequipa", destination + "arequipa").execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {

            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.GREEN)
                    .width(10);

            PolylineOptions rutaCC08 = new PolylineOptions()
                    .add(new LatLng(-16.414941, -71.492536))
                    .add(new LatLng(-16.415216, -71.494284))
                    .add(new LatLng(-16.416492, -71.494072))
                    .add(new LatLng(-16.416857, -71.496609))
                    .add(new LatLng(-16.415499, -71.497178))
                    .add(new LatLng(-16.413657, -71.499882))
                    .add(new LatLng(-16.413035, -71.499891))
                    .add(new LatLng(-16.413056, -71.500478))
                    .add(new LatLng(-16.413280, -71.500497))
                    .add(new LatLng(-16.405268, -71.512664))
                    .add(new LatLng(-16.405201, -71.512795))
                    .add(new LatLng(-16.403016, -71.516164))
                    .add(new LatLng(-16.402967, -71.516303))
                    .add(new LatLng(-16.402277, -71.517215))
                    .add(new LatLng(-16.402264, -71.517322))
                    .add(new LatLng(-16.402002, -71.517719))
                    .add(new LatLng(-16.399419, -71.521565))
                    .add(new LatLng(-16.399983, -71.522128))
                    .add(new LatLng(-16.408806, -71.532933))
                    .add(new LatLng(-16.408325, -71.533418))
                    .add(new LatLng(-16.407660, -71.533937))
                    .add(new LatLng(-16.405869, -71.531649))
                    .add(new LatLng(-16.405891, -71.531619))
                    .add(new LatLng(-16.406521, -71.531150))
                    .add(new LatLng(-16.407015, -71.530597))
                    .add(new LatLng(-16.404406, -71.527456))
                    .add(new LatLng(-16.404818, -71.527038))
                    .add(new LatLng(-16.405201, -71.526531))
                    .add(new LatLng(-16.406503, -71.525238))
                    .add(new LatLng(-16.407023, -71.525997))
                    .add(new LatLng(-16.407144, -71.526429))
                    .add(new LatLng(-16.407515, -71.527124))
                    .add(new LatLng(-16.407615, -71.527111))
                    .add(new LatLng(-16.407898, -71.526864))
                    .add(new LatLng(-16.408271, -71.526722))
                    .add(new LatLng(-16.407955, -71.525810))
                    .add(new LatLng(-16.407253, -71.524721))
                    .add(new LatLng(-16.406893, -71.523831))
                    .add(new LatLng(-16.406710, -71.523394))
                    .add(new LatLng(-16.405750, -71.522112))
                    .add(new LatLng(-16.404955, -71.520572))
                    .add(new LatLng(-16.404022, -71.519490))
                    .add(new LatLng(-16.404285, -71.519412))
                    .add(new LatLng(-16.403894, -71.518717))
                    .add(new LatLng(-16.403704, -71.517971))
                    .add(new LatLng(-16.403346, -71.517288))
                    .add(new LatLng(-16.403241, -71.516625))
                    .add(new LatLng(-16.403141, -71.516459))
                    .add(new LatLng(-16.403401, -71.516435))
                    .add(new LatLng(-16.405520, -71.515537))
                    .add(new LatLng(-16.405474, -71.515368))
                    .add(new LatLng(-16.404609, -71.513930))
                    .add(new LatLng(-16.405813, -71.512074))
                    .add(new LatLng(-16.406634, -71.510770))
                    .add(new LatLng(-16.407493, -71.509424))
                    .add(new LatLng(-16.408396, -71.508013))
                    .add(new LatLng(-16.408772, -71.507369))
                    .add(new LatLng(-16.410192, -71.505263))
                    .add(new LatLng(-16.410362, -71.504973))
                    .add(new LatLng(-16.411019, -71.503995))
                    .add(new LatLng(-16.412185, -71.502238))
                    .add(new LatLng(-16.413464, -71.500309))
                    .add(new LatLng(-16.414810, -71.498252))
                    .add(new LatLng(-16.415352, -71.497419))
                    .add(new LatLng(-16.415648, -71.497140))
                    .add(new LatLng(-16.416891, -71.496652))
                    .add(new LatLng(-16.416515, -71.494077))
                    .add(new LatLng(-16.415213, -71.494270))
                    .add(new LatLng(-16.414922, -71.492446))
                    .add(new LatLng(-16.414359, -71.492567))
                    .geodesic(true)
                    .color(Color.GRAY)
                    .width(10);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .title("Empresa 15 de Agosto S.A.")
                    .snippet("Ruta C C08")
                    .position(rutaCC08.getPoints().get(0))));

//            PolylineOptions rutaCC08vuelta = new PolylineOptions()
//
//                    .geodesic(true)
//                    .color(Color.RED)
//                    .width(10);
//            originMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
//                    .title("Empresa 15 de Agosto S.A.")
//                    .snippet("Ruta C C08 de Vuelta")
//                    .position(rutaCC08vuelta.getPoints().get(0))));

            System.out.println("Tamaño transporte publico" + rutaCC08.getPoints().size());
            //System.out.println("Tamaño transporte publico" + rutaCC08vuelta.getPoints().size());
            System.out.println("Tamaño usuario" + route.points.size());

            //RUTA C C08 DE IDA
            if (rutaCC08.getPoints().size() > route.points.size()) {
                int p = 0;
                for (int i = 0; i < rutaCC08.getPoints().size(); i++) {
                    if (route.points.get(p) == null) {
                        p = 0;
                    } else if (route.points.get(p) == rutaCC08.getPoints().get(i)) {
                        System.out.println("Coincidencia: " + rutacc08idaC);
                        rutacc08idaC++;
                        p++;
                    }
                    polylineOptions.add(route.points.get(p));
                }
            } else if (rutaCC08.getPoints().size() < route.points.size()) {
                for (int i = 0, p = 0; i < route.points.size(); i++) {
                    if (rutaCC08.getPoints().get(p) == null) {
                        p = 0;
                    } else if (route.points.get(i) == rutaCC08.getPoints().get(p)) {
                        System.out.println("Coincidencia: " + rutacc08idaC);
                        rutacc08idaC++;
                        p++;
                    }
                    polylineOptions.add(route.points.get(i));
                }
            } else if (rutaCC08.getPoints().size() == route.points.size()) {
                for (int i = 0; i < route.points.size(); i++) {
                    if (route.points.get(i) == rutaCC08.getPoints().get(i)) {
                        System.out.println("Coincidencia: " + rutaCC08);
                        rutacc08idaC++;
                    }
                    polylineOptions.add(route.points.get(i));
                }
            }
            //RUTA C C08 DE VUELTA
//            if (rutaCC08vuelta.getPoints().size() > route.points.size()) {
//                int p = 0;
//                for (int i = 0; i < rutaCC08vuelta.getPoints().size(); i++) {
//                    if (route.points.get(p) == null) {
//                        p = 0;
//                    } else if (route.points.get(p) == rutaCC08vuelta.getPoints().get(i)) {
//                        System.out.println("Coincidencia: " + rutacc08vueltaC);
//                        rutacc08vueltaC++;
//                        p++;
//                    }
//                    polylineOptions.add(route.points.get(p));
//                }
//            } else if (rutaCC08vuelta.getPoints().size() < route.points.size()) {
//                for (int i = 0, p = 0; i < route.points.size(); i++) {
//                    if (rutaCC08vuelta.getPoints().get(p) == null) {
//                        p = 0;
//                    } else if (route.points.get(i) == rutaCC08vuelta.getPoints().get(p)) {
//                        System.out.println("Coincidencia: " + rutacc08vueltaC);
//                        rutacc08vueltaC++;
//                        p++;
//                    }
//                    polylineOptions.add(route.points.get(i));
//                }
//            } else if (rutaCC08vuelta.getPoints().size() == route.points.size()) {
//                for (int i = 0; i < route.points.size(); i++) {
//                    if (route.points.get(i) == rutaCC08vuelta.getPoints().get(i)) {
//                        System.out.println("Coincidencia: " + rutacc08vueltaC);
//                        rutacc08vueltaC++;
//                    }
//                    polylineOptions.add(route.points.get(i));
//                }
//            }


            polylinePaths.add(mMap.addPolyline(polylineOptions));
            polylinePaths.add(mMap.addPolyline(rutaCC08));
            //polylinePaths.add(mMap.addPolyline(rutaCC08vuelta));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 20));
        }
    }
    //                float distancia = formulaHaversine(route.points.get(i), rutaCC08ida.getPoints().get(0));
//                System.out.println("Distancia: " + distancia);

//            if (rutaCC08ida.getPoints().size() < route.points.size()) {
//                for (int i = 0; i < rutaCC08ida.getPoints().size(); i++) {
//                    float distancia = formulaHaversine(route.points.get(i), rutaCC08ida.getPoints().get(i));
//                    System.out.println("Distancia: " + distancia);
////                    if (route.points.get(i) == rutaCC08ida.getPoints().get(i)) {
////                        System.out.println("Puntos de Latitud y Logitud Iguales");
////                    }
//                    polylineOptions.add(route.points.get(i));
//                }
//            } else if (rutaCC08ida.getPoints().size() > route.points.size()) {
//
//            } else {
//                for (int i = 0; i < route.points.size(); i++) {
//                    float distancia = formulaHaversine(route.points.get(i), rutaCC08ida.getPoints().get(i));
//                    System.out.println("Distancia: " + distancia);
////                    if (route.points.get(i) == rutaCC08ida.getPoints().get(i)) {
////                        System.out.println("Puntos de Latitud y Logitud Iguales");
////                    }
//                    polylineOptions.add(route.points.get(i));
//                }
//            }


    private float formulaHaversine(LatLng latlng, LatLng latLng1) {
        float radioTierra = 6378.0F;
        float difLatitut = convertirRadianes(latlng.latitude - latLng1.latitude);
        float difLongitude = convertirRadianes(latlng.longitude - latLng1.longitude);
        float a = alCuadrado(Math.sin(difLatitut / 2)) + alCuadrado(Math.cos(convertirRadianes(latlng.latitude))) + alCuadrado(Math.cos(convertirRadianes(latLng1.latitude))) + alCuadrado(Math.sin(difLongitude / 2));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));

        return radioTierra * convertirRadianes(c);
    }

    private float alCuadrado(double valor) {
        return (float) Math.pow(valor, 2);
    }

    private float convertirRadianes(double v) {
        return (float) ((float) Math.toDegrees(Math.PI / 180) * v);
    }
}
