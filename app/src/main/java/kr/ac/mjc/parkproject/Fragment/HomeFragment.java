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
    private static final Map<String, String> placeDescriptionMap = new HashMap<>();
    private static final Map<String, String> placeInfoMap = new HashMap<>();

    static {
        // 주소 맵
        placeAddressMap.put("T익스프레스", "경기도 용인시 처인구 포곡읍 에버랜드로 199");
        placeAddressMap.put("아마존 익스프레스", "경기도 용인시 처인구 에버랜드로 199");
        placeAddressMap.put("사파리 월드", "경기도 용인시 처인구 에버랜드로 199");
        placeAddressMap.put("피터팬", "경기도 용인시 처인구 포곡읍 가실리 104-9");
        placeAddressMap.put("챔피언쉽 로데오", "경기도 용인시 처인구 포곡읍 가실리");
        placeAddressMap.put("썬더폴스", "경기도 용인시 처인구 포곡읍 에버랜드로 204");
        placeAddressMap.put("로얄 쥬빌리 캐로셀", "경기도 용인시 처인구 포곡읍 에버랜드로 199");
        placeAddressMap.put("범퍼카", "경기도 용인시 처인구 포곡읍 가실리 104-50");
        placeAddressMap.put("레이싱 코스터", "경기도 용인시 처인구 포곡읍 가실리 104-24");
        placeAddressMap.put("로스트밸리", "경기도 용인시 처인구 포곡읍 전대리 498-1");
        placeAddressMap.put("판다월드", "경기도 용인시 처인구 포곡읍");

        // 설명 맵
        placeDescriptionMap.put("T익스프레스", "시속 104km의 엄청난 속도, 낙하각 77도의 아찔함!\n" +
                "세계 최고의 우든코스터가 잊지 못할 최고의 기억을\n여러분께 선사합니다.");

        placeDescriptionMap.put("아마존 익스프레스", "많은 탐험가들이 호수 속에 잠들어 있는\n" +
                "황금을 차지하기 위해 보트 위에 몸을 맡겼다!\n" +
                "아마존 밀림 구석구석 장장 580미터 굽이치는 급류 속으로\n" +
                "꿈과 모험을 찾아 떠나는 곳!!");

        placeDescriptionMap.put("사파리 월드", "더 가까이, 코앞에서 생생한 맹수를 만날 기회!\n" +
                "낮아진 시야로 더 가깝고 넓어진 시야로\n더 생생한 와일드트램을 만나보세요.\n");

        placeDescriptionMap.put("피터팬", "번쩍번쩍 불빛이 빛나는 탐험선을 타고\n" +
                "뒤쫓아오는 무서운 후크 선장을 따돌려라!\n" +
                "\n" +
                "신나게 돌고도는 피터팬의 대모험이 이제 시작됩니다!\n");

        placeDescriptionMap.put("챔피언쉽 로데오", "미국 서부시대 분위기의 로데오 경기장에서\n" +
                "신나는 음악과 함께 모두모두 빙글빙글~\n" +
                "\n" +
                "황야의 로데오에서 거친 라이드를\n" +
                "즐길 줄 아는 당신이 바로 챔피온!");

        placeDescriptionMap.put("썬더폴스", "20미터의 국내 최고 낙하높이! 45도의 국내 최대 낙하각도!\n" +
                "국내 최초로 도입된 뒤로 떨어지는 백워드 드롭!\n" +
                "약 40미터 확장 된 최장 수로길이 486미터!");

        placeDescriptionMap.put("로얄 쥬빌리 캐로셀", "백마 탄 왕자와 공주가 만난\n중세 유럽 무도회장의 분위기는 어떨까?\n" +
                "무도회장 행 회전목마가 여러분을 기다립니다.\n" +
                "환상의 꿈결 같은 동화 속 주인공이 되어보세요.");

        placeDescriptionMap.put("범퍼카", "어트랙션의 원조!\n" +
                "누구든 멋진 레이서가 될 수 있어\n" +
                "항상 사랑받는 우리의 범퍼카~\n" +
                "\n" +
                "면허증이 없어도 오케이!\n" +
                "귀여운 자동차를 타고 요리 쿵 조리 쿵 부딪혀보며\n" +
                "재미난 경험을 즐겨 보세요.");

        placeDescriptionMap.put("레이싱 코스터", "빠르게, 더 빠르게!\n" +
                "깡충깡충 토키 허키와 느릿느릿 거북이 티미!\n" +
                "\n" +
                "아직도 끝나지 않은 토끼와 거북이의 달리기 시합이\n" +
                "이제는 신나는 롤러코스터로 이어집니다!\n" +
                "\n" +
                "국내 최초 역방향 롤러코스터\n" +
                "뒤로 떨어지는 재미와 스릴을 즐겨보세요!\n" +
                "좌석이 반대 방향으로 뒤집혀 운행됩니다.");

        placeDescriptionMap.put("로스트밸리", "대자연이 살아 숨쉬는 초식동물 사파리\n" +
                "\n" +
                "기린, 코끼리, 코뿔소\n" +
                "\n" +
                "지상에서 가장 큰 육상 동물을\n" +
                "\n" +
                "보다 가까이에서 만나보세요");

        placeDescriptionMap.put("판다월드", "동물들의 생태환경과 유사하게 자연 친화적으로\n조성된 바오 패밀리가 살고 있는 판다월드!\n" +
                "사랑스러운 패밀리가 보여주는 마법같은 이야기를\n인터랙티브 매직쇼와 함께 판다지아에서 체험해보세요!");
    }

    static {
        placeInfoMap.put("T익스프레스", "10:00 ~ 20:00");
        placeInfoMap.put("아마존 익스프레스", "10:00 ~ 19:30");
        placeInfoMap.put("사파리 월드", "10:30 ~ 18:00");
        placeInfoMap.put("피터팬", "10:00 ~ 21:40");
        placeInfoMap.put("챔피언쉽 로데오", "09:50 ~ 21:00");
        placeInfoMap.put("썬더폴스", "10:30 ~ 20:30");
        placeInfoMap.put("로얄 쥬빌리 캐로셀", "09:50 ~ 21:40");
        placeInfoMap.put("범퍼카", "10:00 ~ 21:00");
        placeInfoMap.put("레이싱 코스터", "09:50 ~ 21:40");
        placeInfoMap.put("로스트밸리", "10:30 ~ 18:00");
        placeInfoMap.put("판다월드", "10:00 ~ 17:30");
    }

    private static final Map<String, String> placeUsageMap = new HashMap<>();

    static {
        placeUsageMap.put("T익스프레스", "130cm~195cm");
        placeUsageMap.put("아마존 익스프레스", "~110cm 보호자동반 필요");
        placeUsageMap.put("사파리 월드", "~100cm 보호자동반 필요");
        placeUsageMap.put("피터팬", "~100cm는 보호자동반 필요");
        placeUsageMap.put("챔피언쉽 로데오", "130cm~");
        placeUsageMap.put("썬더폴스", "100cm~ / ~110cm 보호자동반 필요");
        placeUsageMap.put("로얄 쥬빌리 캐로셀", "~100cm 보호자동반 필요");
        placeUsageMap.put("범퍼카", "120cm~");
        placeUsageMap.put("레이싱 코스터", "100cm~");
        placeUsageMap.put("로스트밸리", "~100cm는 보호자동반 필요");
        placeUsageMap.put("판다월드", "전 연령 관람 가능");
    }

    // 장소 이름에 따른 신장 제한 정보 매핑
    private static final Map<String, String> heightRestrictionMap = new HashMap<>();

    static {
        heightRestrictionMap.put("T익스프레스", "130cm 이상 195cm 이하");
        heightRestrictionMap.put("아마존 익스프레스", "110cm 이상");
        heightRestrictionMap.put("사파리 월드", "100cm 이상");
        heightRestrictionMap.put("피터팬", "100cm 이상");
        heightRestrictionMap.put("챔피언쉽 로데오", "130cm 이상");
        heightRestrictionMap.put("썬더폴스", "100cm 이상");
        heightRestrictionMap.put("로얄 쥬빌리 캐로셀", "100cm 이상");
        heightRestrictionMap.put("범퍼카", "120cm 이상");
        heightRestrictionMap.put("레이싱 코스터", "100cm 이상");
        heightRestrictionMap.put("로스트밸리", "100cm 이상");
        heightRestrictionMap.put("판다월드", "제한 없음");
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

        // 전달된 장소 이름 가져오기
        String placeName = getArguments() != null ? getArguments().getString(ARG_PLACE_NAME) : "장소명 없음";
        String placeAddress = placeAddressMap.getOrDefault(placeName, "주소 정보 없음");
        String placeDescription = placeDescriptionMap.getOrDefault(placeName, "");

        // 텍스트뷰 설정
        TextView nameTextView = view.findViewById(R.id.placeName_tv);
        TextView addressTextView = view.findViewById(R.id.placeLocation_tv);
        TextView descriptionTextView = view.findViewById(R.id.placeDescription_tv);

        TextView infoTextView = view.findViewById(R.id.mapInfoValue_tv);
        String infoText = placeInfoMap.containsKey(placeName) ?
                placeInfoMap.get(placeName) : "운영 정보 없음";
        infoTextView.setText(infoText);

        TextView usageTextView = view.findViewById(R.id.useValue_tv);
        String usageText = placeUsageMap.containsKey(placeName) ?
                placeUsageMap.get(placeName) : "이용조건 정보 없음";
        usageTextView.setText(usageText);

        TextView heightRestrictionTextView = view.findViewById(R.id.hrValue_tv);
        String heightRestriction = heightRestrictionMap.containsKey(placeName) ?
                heightRestrictionMap.get(placeName) : "신장 제한 정보 없음";
        heightRestrictionTextView.setText(heightRestriction);


        nameTextView.setText(placeName);
        addressTextView.setText(placeAddress);

        if (!placeDescription.isEmpty()) {
            descriptionTextView.setText(placeDescription);
            descriptionTextView.setVisibility(View.VISIBLE);
        } else {
            descriptionTextView.setVisibility(View.GONE);
        }

        return view;
    }
}
