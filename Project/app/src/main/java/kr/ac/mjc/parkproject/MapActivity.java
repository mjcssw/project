package kr.ac.mjc.parkproject;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker currentMarker; // 현재 표시된 마커 저장용 (하나만 표시할 경우)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 초기 위치: 에버랜드
        LatLng everland = new LatLng(37.2946, 127.2022);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(everland, 15));

        // 지도 클릭 리스너 설정
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // 기존 마커 제거 (하나만 표시할 경우)
                if (currentMarker != null) {
                    currentMarker.remove();
                }

                // 새 마커 추가
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng));

                // 마커 정보창 바로 표시
                if (currentMarker != null) {
                    currentMarker.showInfoWindow();
                }
            }
        });
    }

}
