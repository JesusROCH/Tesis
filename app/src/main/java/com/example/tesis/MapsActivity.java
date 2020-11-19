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
    private int rutacc08C,rutae011C;

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
                    .snippet("Recorrido de ida")
                    .position(rutaCC08.getPoints().get(0))));
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .title("Empresa 15 de Agosto S.A.")
                    .snippet("Recorrido de vuelta")
                    .position(new LatLng(-16.405891, -71.531619))));

            PolylineOptions rutae011 = new PolylineOptions()
                    .add(new LatLng(-16.389760, -71.547713))
                    .add(new LatLng(-16.390458, -71.548074))
                    .add(new LatLng(-16.391238, -71.547546))
                    .add(new LatLng(-16.391637, -71.547377))
                    .add(new LatLng(-16.392432, -71.547133))
                    .add(new LatLng(-16.392234, -71.546221))
                    .add(new LatLng(-16.392499, -71.545896))
                    .add(new LatLng(-16.393132, -71.545470))
                    .add(new LatLng(-16.393503, -71.544998))
                    .add(new LatLng(-16.394409, -71.544314))
                    .add(new LatLng(-16.396053, -71.544971))
                    .add(new LatLng(-16.396946, -71.543386))
                    .add(new LatLng(-16.396951, -71.542686))
                    .add(new LatLng(-16.396920, -71.542619))
                    .add(new LatLng(-16.396328, -71.542638))
                    .add(new LatLng(-16.395710, -71.542485))
                    .add(new LatLng(-16.395391, -71.542346))
                    .add(new LatLng(-16.394781, -71.541834))
                    .add(new LatLng(-16.393458, -71.541247))
                    .add(new LatLng(-16.392969, -71.541075))
                    .add(new LatLng(-16.392205, -71.540557))
                    .add(new LatLng(-16.392666, -71.540045))
                    .add(new LatLng(-16.393149, -71.539641))
                    .add(new LatLng(-16.393777, -71.539147))
                    .add(new LatLng(-16.394279, -71.539058))
                    .add(new LatLng(-16.394881, -71.539203))
                    .add(new LatLng(-16.395249, -71.539377))
                    .add(new LatLng(-16.395694, -71.539790))
                    .add(new LatLng(-16.395830, -71.539720))
                    .add(new LatLng(-16.395256, -71.539189))
                    .add(new LatLng(-16.394680, -71.539009))
                    .add(new LatLng(-16.393540, -71.538760))
                    .add(new LatLng(-16.393267, -71.538637))
                    .add(new LatLng(-16.392801, -71.538259))
                    .add(new LatLng(-16.392644, -71.537996))
                    .add(new LatLng(-16.392603, -71.537682))
                    .add(new LatLng(-16.393076, -71.536459))
                    .add(new LatLng(-16.393287, -71.535865))
                    .add(new LatLng(-16.393331, -71.535463))
                    .add(new LatLng(-16.393946, -71.535715))
                    .add(new LatLng(-16.393560, -71.536734))
                    .add(new LatLng(-16.393371, -71.537379))
                    .add(new LatLng(-16.393682, -71.537465))
                    .add(new LatLng(-16.394238, -71.537653))
                    .add(new LatLng(-16.394848, -71.537878))
                    .add(new LatLng(-16.396371, -71.538460))
                    .add(new LatLng(-16.397266, -71.538811))
                    .add(new LatLng(-16.397536, -71.538930))
                    .add(new LatLng(-16.398006, -71.539308))
                    .add(new LatLng(-16.398315, -71.539466))
                    .add(new LatLng(-16.398806, -71.539742))
                    .add(new LatLng(-16.399308, -71.540021))
                    .add(new LatLng(-16.399825, -71.540254))
                    .add(new LatLng(-16.400360, -71.540479))
                    .add(new LatLng(-16.400597, -71.540576))
                    .add(new LatLng(-16.401117, -71.540469))
                    .add(new LatLng(-16.401490, -71.540268))
                    .add(new LatLng(-16.402043, -71.539919))
                    .add(new LatLng(-16.402421, -71.538851))
                    .add(new LatLng(-16.403998, -71.539436))
                    .add(new LatLng(-16.404518, -71.538508))
                    .add(new LatLng(-16.405023, -71.537635))
                    .add(new LatLng(-16.405695, -71.535712))
                    .add(new LatLng(-16.405839, -71.535682))
                    .add(new LatLng(-16.405924, -71.535508))
                    .add(new LatLng(-16.405896, -71.535387))
                    .add(new LatLng(-16.406372, -71.535092))
                    .add(new LatLng(-16.406863, -71.534759))
                    .add(new LatLng(-16.407653, -71.533953))
                    .add(new LatLng(-16.407728, -71.534004))
                    .add(new LatLng(-16.407715, -71.534084))
                    .add(new LatLng(-16.407802, -71.534146))
                    .add(new LatLng(-16.407866, -71.534108))
                    .add(new LatLng(-16.407910, -71.534028))
                    .add(new LatLng(-16.408795, -71.534213))
                    .add(new LatLng(-16.409330, -71.533778))
                    .add(new LatLng(-16.409799, -71.533388))
                    .add(new LatLng(-16.410298, -71.532956))
                    .add(new LatLng(-16.410442, -71.532921))
                    .add(new LatLng(-16.410529, -71.532784))
                    .add(new LatLng(-16.409917, -71.531564))
                    .add(new LatLng(-16.409415, -71.530266))
                    .add(new LatLng(-16.409258, -71.530346))
                    .add(new LatLng(-16.409091, -71.529598))
                    .add(new LatLng(-16.408924, -71.528646))
                    .add(new LatLng(-16.408803, -71.528182))
                    .add(new LatLng(-16.408633, -71.527734))
                    .add(new LatLng(-16.408455, -71.527294))
                    .add(new LatLng(-16.408270, -71.526723))
                    .add(new LatLng(-16.407869, -71.526903))
                    .add(new LatLng(-16.407553, -71.527166))
                    .add(new LatLng(-16.407222, -71.526695))
                    .add(new LatLng(-16.407119, -71.526121))
                    .add(new LatLng(-16.406512, -71.525209))
                    .add(new LatLng(-16.406144, -71.525614))
                    .add(new LatLng(-16.405323, -71.526373))
                    .add(new LatLng(-16.404996, -71.526730))
                    .add(new LatLng(-16.404615, -71.527226))
                    .add(new LatLng(-16.404363, -71.527443))
                    //Recorrido de vuelta
                    .add(new LatLng(-16.404306, -71.527379))
                    .add(new LatLng(-16.402929, -71.525692))
                    .add(new LatLng(-16.401920, -71.524464))
                    .add(new LatLng(-16.401601, -71.524751))
                    .add(new LatLng(-16.401099, -71.525191))
                    .add(new LatLng(-16.400299, -71.525894))
                    .add(new LatLng(-16.399928, -71.526221))
                    .add(new LatLng(-16.399527, -71.525760))
                    .add(new LatLng(-16.398328, -71.526830))
                    .add(new LatLng(-16.397044, -71.527798))
                    .add(new LatLng(-16.397044, -71.527798))
                    .add(new LatLng(-16.396417, -71.528283))
                    .add(new LatLng(-16.396355, -71.528420))
                    .add(new LatLng(-16.396355, -71.528420))
                    .add(new LatLng(-16.396352, -71.529477))
                    .add(new LatLng(-16.396275, -71.529697))
                    .add(new LatLng(-16.395776, -71.530333))
                    .add(new LatLng(-16.395557, -71.530676))
                    .add(new LatLng(-16.395382, -71.531202))
                    .add(new LatLng(-16.395426, -71.531301))
                    .add(new LatLng(-16.395076, -71.532371))
                    .add(new LatLng(-16.394713, -71.533492))
                    .add(new LatLng(-16.394111, -71.535234))
                    .add(new LatLng(-16.393851, -71.535967))
                    .add(new LatLng(-16.393550, -71.536731))
                    .add(new LatLng(-16.393371, -71.537374))
                    .add(new LatLng(-16.393690, -71.537465))
                    .add(new LatLng(-16.394845, -71.537875))
                    .add(new LatLng(-16.396373, -71.538454))
                    .add(new LatLng(-16.397539, -71.538931))
                    .add(new LatLng(-16.397812, -71.539146))
                    .add(new LatLng(-16.398098, -71.539374))
                    .add(new LatLng(-16.398440, -71.539529))
                    .add(new LatLng(-16.398167, -71.540259))
                    .add(new LatLng(-16.398074, -71.540597))
                    .add(new LatLng(-16.397572, -71.541922))
                    .add(new LatLng(-16.397371, -71.542177))
                    .add(new LatLng(-16.396923, -71.542606))
                    .add(new LatLng(-16.396951, -71.542681))
                    .add(new LatLng(-16.396954, -71.543381))
                    .add(new LatLng(-16.396066, -71.544966))
                    .add(new LatLng(-16.397856, -71.545697))
                    .add(new LatLng(-16.399117, -71.546204))
                    .add(new LatLng(-16.399724, -71.546443))
                    .add(new LatLng(-16.400347, -71.546483))
                    .add(new LatLng(-16.401327, -71.546842))
                    .add(new LatLng(-16.401124, -71.547427))
                    .add(new LatLng(-16.400764, -71.548516))
                    .add(new LatLng(-16.400604, -71.548959))
                    .add(new LatLng(-16.400393, -71.549415))
                    .add(new LatLng(-16.400087, -71.549882))
                    .add(new LatLng(-16.399423, -71.549346))
                    .add(new LatLng(-16.398292, -71.548344))
                    .add(new LatLng(-16.397548, -71.547652))
                    .add(new LatLng(-16.397432, -71.547464))
                    .add(new LatLng(-16.397370, -71.547271))
                    .add(new LatLng(-16.397195, -71.547207))
                    .add(new LatLng(-16.397035, -71.547357))
                    .add(new LatLng(-16.395913, -71.547907))
                    .add(new LatLng(-16.394964, -71.548398))
                    .add(new LatLng(-16.394092, -71.548819))
                    .add(new LatLng(-16.393603, -71.549044))
                    .add(new LatLng(-16.393204, -71.549103))
                    .add(new LatLng(-16.392581, -71.548886))
                    .add(new LatLng(-16.391634, -71.548443))
                    .add(new LatLng(-16.390728, -71.548035))
                    .add(new LatLng(-16.389820, -71.547627))
                    .geodesic(true)
                    .color(Color.GRAY)
                    .width(10);
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .title("Empresa 24 de Diciembre S.R.L.")
                    .snippet("Recorrido de ida")
                    .position(rutae011.getPoints().get(0))));
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .title("Empresa 24 de Diciembre S.R.L.")
                    .snippet("Recorrido de vuelta")
                    .position(new LatLng(-16.404306, -71.527379))));


            System.out.println("Tamaño transporte publico" + rutaCC08.getPoints().size());
            System.out.println("Tamaño transporte publico" + rutae011.getPoints().size());
            System.out.println("Tamaño usuario" + route.points.size());

            //RUTA C-C08
            if (rutaCC08.getPoints().size() > route.points.size()) {
                int p = 0;
                for (int i = 0; i < rutaCC08.getPoints().size(); i++) {
                    double d = distance(route.points.get(p).latitude,rutaCC08.getPoints().get(i).latitude,route.points.get(p).longitude,rutaCC08.getPoints().get(i).longitude);
                    System.out.println("Distancia:"+d);
                    if (route.points.get(p) == null) {
                        p = 0;
                    } else if (d<1000) {
                        System.out.println("Coincidencia: " + rutacc08C);
                        rutacc08C++;
                        p++;
                    }
                    polylineOptions.add(route.points.get(p));
                }
            } else if (rutaCC08.getPoints().size() < route.points.size()) {
                for (int i = 0, p = 0; i < route.points.size(); i++) {
                    double d = distance(route.points.get(p).latitude,rutaCC08.getPoints().get(i).latitude,route.points.get(p).longitude,rutaCC08.getPoints().get(i).longitude);
                    System.out.println("Distancia:"+d);
                    if (rutaCC08.getPoints().get(p) == null) {
                        p = 0;
                    } else if (d<1000) {
                        System.out.println("Coincidencia: " + rutacc08C);
                        rutacc08C++;
                        p++;
                    }
                    polylineOptions.add(route.points.get(i));
                }
            } else if (rutaCC08.getPoints().size() == route.points.size()) {
                for (int i = 0; i < route.points.size(); i++) {
                    double d = distance(route.points.get(i).latitude,rutaCC08.getPoints().get(i).latitude,route.points.get(i).longitude,rutaCC08.getPoints().get(i).longitude);
                    System.out.println("Distancia:"+d);
                    if (d<1000) {
                        System.out.println("Coincidencia: " + rutacc08C);
                        rutacc08C++;
                    }
                    polylineOptions.add(route.points.get(i));
                }
            }
            //ruta e011
            if (rutae011.getPoints().size() > route.points.size()) {
                int p = 0;
                for (int i = 0; i < rutae011.getPoints().size(); i++) {
                    double d = distance(route.points.get(p).latitude,rutae011.getPoints().get(i).latitude,route.points.get(p).longitude,rutae011.getPoints().get(i).longitude);
                    System.out.println("Distancia:"+d);
                    if (route.points.get(p) == null) {
                        p = 0;
                    } else if (d<1000) {
                        System.out.println("Coincidencia: " + rutae011C);
                        rutae011C++;
                        p++;
                    }
                    polylineOptions.add(route.points.get(p));
                }
            } else if (rutae011.getPoints().size() < route.points.size()) {
                for (int i = 0, p = 0; i < route.points.size(); i++) {
                    double d = distance(route.points.get(p).latitude,rutae011.getPoints().get(i).latitude,route.points.get(p).longitude,rutae011.getPoints().get(i).longitude);
                    System.out.println("Distancia:"+d);
                    if (rutae011.getPoints().get(p) == null) {
                        p = 0;
                    } else if (d<1000) {
                        System.out.println("Coincidencia: " + rutae011C);
                        rutae011C++;
                        p++;
                    }
                    polylineOptions.add(route.points.get(i));
                }
            } else if (rutae011.getPoints().size() == route.points.size()) {
                for (int i = 0; i < route.points.size(); i++) {
                    double d = distance(route.points.get(i).latitude,rutae011.getPoints().get(i).latitude,route.points.get(i).longitude,rutae011.getPoints().get(i).longitude);
                    System.out.println("Distancia:"+d);
                    if (d<1000) {
                        System.out.println("Coincidencia: " + rutae011C);
                        rutae011C++;
                    }
                    polylineOptions.add(route.points.get(i));
                }
            }

            System.out.println("ruta cc08: " + rutacc08C);
            System.out.println("ruta e011: " + rutae011C);

            if(rutacc08C<rutae011C) {
                polylinePaths.add(mMap.addPolyline(rutae011));
                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }else if (rutae011C<rutacc08C) {
                polylinePaths.add(mMap.addPolyline(rutaCC08));
                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 20));
        }
    }


    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance/2) * Math.sin(latDistance/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance/2) * Math.sin(lonDistance/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
