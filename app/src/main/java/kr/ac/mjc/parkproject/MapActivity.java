package kr.ac.mjc.parkproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import kr.ac.mjc.parkproject.Fragment.PlaceBottomSheetFragment;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // 에버랜드 명소 좌표
    private final LatLng[] everlandAttractions = new LatLng[] {
            new LatLng(37.289972, 127.202612), // T익스프레스
            new LatLng(37.291327, 127.205943), // 아마존 익스프레스
            new LatLng(37.291163, 127.205061), // 사파리 월드
            new LatLng(37.292735, 127.199061), // 피터팬
            new LatLng(37.294202, 127.200833), // 챔피언쉽 로데오
            new LatLng(37.293676, 127.198577), // 썬더폴스
            new LatLng(37.291697, 127.199631), // 로얄 쥬빌리 캐로셀
            new LatLng(37.292291, 127.199561), // 범퍼카
            new LatLng(37.293798, 127.200964), // 레이싱 코스터
            new LatLng(37.291358, 127.209972), // 로스트밸리
            new LatLng(37.293448, 127.202817)  // 판다월드
    };

    // 각 명소에 대응되는 이름 배열
    private final String[] attractionNames = new String[] {
            "T익스프레스",
            "아마존 익스프레스",
            "사파리 월드",
            "피터팬",
            "챔피언쉽 로데오",
            "썬더폴스",
            "회전목마",
            "범퍼카",
            "레이싱 코스터",
            "로스트밸리",
            "판다월드"
    };

    private final List<Marker> everlandMarkers = new ArrayList<>();

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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 지도 초기 위치 설정
        LatLng everland = new LatLng(37.2946, 127.2022);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(everland, 17));

        // 위치 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        // 마커 추가
        for (int i = 0; i < everlandAttractions.length; i++) {
            LatLng attraction = everlandAttractions[i];
            String name = attractionNames[i];

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(attraction)
                    .title(name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            if (marker != null) {
                everlandMarkers.add(marker);
            }
        }

        // 마커 클릭 시: 해당 명소 바텀시트 표시
        mMap.setOnMarkerClickListener(marker -> {
            if (everlandMarkers.contains(marker)) {
                String placeName = marker.getTitle();
                PlaceBottomSheetFragment bottomSheet = PlaceBottomSheetFragment.newInstance(placeName);
                bottomSheet.show(getSupportFragmentManager(), "PlaceBottomSheet");
                return true;
            }
            return false;
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
