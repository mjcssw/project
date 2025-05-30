package kr.ac.mjc.parkproject.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import kr.ac.mjc.parkproject.R;
import kr.ac.mjc.parkproject.Adapter.ViewPagerAdapter;

public class PlaceBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_PLACE_NAME = "place_name";
    private String placeName;

    // ✅ 안전한 인스턴스 생성용 정적 메서드
    public static PlaceBottomSheetFragment newInstance(String placeName) {
        PlaceBottomSheetFragment fragment = new PlaceBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_NAME, placeName);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceBottomSheetFragment() {
        // 반드시 빈 생성자 필요!
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_bottom_sheet, container, false);

        if (getArguments() != null) {
            placeName = getArguments().getString(ARG_PLACE_NAME);
        }

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance(placeName));
        fragmentList.add(new PhotoFragment());
        fragmentList.add(new ReviewFragment());
        fragmentList.add(new InfoFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragmentList);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText("홈"); break;
                        case 1: tab.setText("사진"); break;
                        case 2: tab.setText("후기"); break;
                        case 3: tab.setText("정보"); break;
                    }
                }).attach();

        return view;
    }
}
