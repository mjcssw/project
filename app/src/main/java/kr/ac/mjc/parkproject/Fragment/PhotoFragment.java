package kr.ac.mjc.parkproject.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.mjc.parkproject.Adapter.PhotoAdapter;
import kr.ac.mjc.parkproject.R;

public class PhotoFragment extends Fragment {

    private ArrayList<Uri> photoUris = new ArrayList<>();
    private PhotoAdapter adapter;
    private RecyclerView recyclerView;

    private ActivityResultLauncher<Intent> photoPickerLauncher;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        Button buttonUpload = view.findViewById(R.id.button_upload_photo);
        recyclerView = view.findViewById(R.id.recyclerView_photos);

        // RecyclerView에 GridLayoutManager 적용 (3열)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // 어댑터 생성 및 연결
        adapter = new PhotoAdapter(photoUris);
        recyclerView.setAdapter(adapter);

        // 사진 선택 결과 받는 launcher 초기화
        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            // 새 사진을 리스트 앞에 추가
                            photoUris.add(0, selectedImageUri);
                            adapter.notifyItemInserted(0);
                            recyclerView.scrollToPosition(0);

                            // 업로드 성공 메시지
                            Toast.makeText(getContext(), "사진이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            // URI가 null인 경우 실패 메시지
                            Toast.makeText(getContext(), "사진 업로드가 실패되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // resultCode가 OK가 아닌 경우 실패 메시지
                        Toast.makeText(getContext(), "사진 업로드가 실패되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // 버튼 클릭 시 갤러리 열기
        buttonUpload.setOnClickListener(v -> openPhotoPicker());

        return view;
    }

    private void openPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerLauncher.launch(intent);
    }
}
