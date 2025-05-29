package kr.ac.mjc.parkproject.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import kr.ac.mjc.parkproject.R;

public class HomeFragment extends Fragment {

    private static final String ARG_PLACE_NAME = "placeName_tv";

    // 장소 이름에 따른 주소 매핑
    private static final Map<String, String> placeAddressMap = new HashMap<>();

    static {
        placeAddressMap.put("T익스프레스", "경기도 용인시 처인구 포곡읍 에버랜드로 199");
        placeAddressMap.put("아마존 익스프레스", "에버랜드로,경기도 용인시 처인구 포곡읍");
        placeAddressMap.put("사파리 월드", "경기도 용인시 처인구 에버랜드로 199");
        placeAddressMap.put("피터팬", "경기도 용인시 처인구 포곡읍 가실리 104-9");
        placeAddressMap.put("챔피언쉽 로데오", "경기도 용인시 처인구 포곡읍 가실리");
        placeAddressMap.put("썬더폴스", "경기도 용인시 처인구 포곡읍 에버랜드로 204");
        placeAddressMap.put("회전목마", "경기도 용인시 처인구 포곡읍 에버랜드로 199");
        placeAddressMap.put("범퍼카", "경기도 용인시 처인구 포곡읍 가실리 104-50");
        placeAddressMap.put("레이싱 코스터", "경기도 용인시 처인구 포곡읍 가실리 104-24");
        placeAddressMap.put("로스트밸리", "경기도 용인시 처인구 포곡읍 전대리 498-1");
        placeAddressMap.put("판다월드", "경기도 용인시 처인구 포곡읍");
    }

    public static HomeFragment newInstance(String placeName) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_NAME, placeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        String placeName = getArguments() != null ? getArguments().getString(ARG_PLACE_NAME) : "장소명 없음";
        String placeAddress = placeAddressMap.containsKey(placeName) ?
                placeAddressMap.get(placeName) : "주소 정보 없음";

        TextView nameTextView = view.findViewById(R.id.placeName_tv);
        nameTextView.setText(placeName);

        TextView addressTextView = view.findViewById(R.id.placeLocation_tv);
        addressTextView.setText(placeAddress);

        return view;
    }
}
