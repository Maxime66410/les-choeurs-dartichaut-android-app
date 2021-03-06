package fr.lyceejulesfil.leschursdartichaut.ui.googlemaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.lyceejulesfil.leschursdartichaut.databinding.FragmentGalleryBinding;

import fr.lyceejulesfil.leschursdartichaut.R;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMapsViewModel galleryViewModel;
    private GoogleMap mMap; // Création d'un GoogleMap
    public MapView mapView; // CRéation d'un carte google map

    public static MapFragment newInstance(String param1, String param2)
    {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    // Quand le fragment et ouvert
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Demande les permissions
        try {
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                // Mise en place de la MapView
                mapView = (MapView) v.findViewById(R.id.mapview);
                mapView.onCreate(savedInstanceState);

                mapView.getMapAsync(this);
            }
            else if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(),"Permission GPS non accordé.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    // Dès que la map et prête
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Setup des paramètres
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);

        // Mise en place des marqueur
        LatLng Vil = new LatLng(42.727218, 2.983788);
        LatLng VilAd = new LatLng(42.727095, 2.983776);
        mMap.addMarker(new MarkerOptions().position(Vil).title("Lieu de l\'association"));
        mMap.addMarker(new MarkerOptions().position(VilAd).title("3 Rue Saint-Marcel, 66410 Villelongue-de-la-Salanque"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Vil,300)); // Zoom vers la zone
        Toast.makeText(getContext(),"Carte chargé avec succès !", Toast.LENGTH_SHORT).show();
    }

    // Quand on reviens sur l'application
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    // Quand on met en pause l'application
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    // Quand on ferme l'application
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    // Quand on a pas beaucoup de mémoire
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}