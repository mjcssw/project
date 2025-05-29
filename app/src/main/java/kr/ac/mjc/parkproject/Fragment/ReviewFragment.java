package kr.ac.mjc.parkproject.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import kr.ac.mjc.parkproject.R;

public class ReviewFragment extends Fragment {

    private RatingBar ratingBar;
    private EditText reviewEditText;
    private TextView charCountTextView;
    private Button submitButton;
    private LinearLayout reviewContainer;

    private final int MAX_CHAR_COUNT = 100;
    private List<Review> reviewList = new ArrayList<>();

    private static class Review {
        float rating;
        String text;

        Review(float rating, String text) {
            this.rating = rating;
            this.text = text;
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review, container, false);

        ratingBar = view.findViewById(R.id.ratingBar);
        reviewEditText = view.findViewById(R.id.reviewEditText);
        charCountTextView = view.findViewById(R.id.charCountTextView);
        submitButton = view.findViewById(R.id.submitButton);
        reviewContainer = view.findViewById(R.id.reviewContainer);

        submitButton.setEnabled(false);

        reviewEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                charCountTextView.setText(length + "/" + MAX_CHAR_COUNT);

                if (length > MAX_CHAR_COUNT) {
                    reviewEditText.setText(s.subSequence(0, MAX_CHAR_COUNT));
                    reviewEditText.setSelection(MAX_CHAR_COUNT);
                }

                updateSubmitButtonState();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> updateSubmitButtonState());

        submitButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String reviewText = reviewEditText.getText().toString().trim();

            if (rating < 1.0f) {
                Toast.makeText(getContext(), "별점을 최소 1점 이상 선택해주세요.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(reviewText)) {
                Toast.makeText(getContext(), "후기를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(getContext())
                        .setMessage("후기를 등록하시겠습니까?")
                        .setPositiveButton("등록", (dialog, which) -> {
                            boolean added = reviewList.add(new Review(rating, reviewText));
                            if (added) {
                                Toast.makeText(getContext(), "후기가 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                refreshReviews();
                                ratingBar.setRating(0);
                                reviewEditText.setText("");
                                submitButton.setEnabled(false);
                            } else {
                                Toast.makeText(getContext(), "후기 등록이 실패되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

        refreshReviews();

        return view;
    }

    private void updateSubmitButtonState() {
        String reviewText = reviewEditText.getText().toString().trim();
        float rating = ratingBar.getRating();
        submitButton.setEnabled(!TextUtils.isEmpty(reviewText) && rating >= 1.0f);
    }

    private void refreshReviews() {
        reviewContainer.removeAllViews();
        for (int i = reviewList.size() - 1; i >= 0; i--) {
            Review review = reviewList.get(i);
            addReviewView(review.rating, review.text);
        }
    }

    private String getStarString(float rating) {
        int fullStars = (int) rating;
        int maxStars = 5;
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < fullStars; i++) stars.append("★");
        for (int i = fullStars; i < maxStars; i++) stars.append("☆");
        return stars.toString();
    }

    private void addReviewView(float rating, String reviewText) {
        LinearLayout reviewItem = new LinearLayout(getContext());
        reviewItem.setOrientation(LinearLayout.HORIZONTAL);
        reviewItem.setPadding(0, 16, 0, 16);
        reviewItem.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout textContainer = new LinearLayout(getContext());
        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView reviewView = new TextView(getContext());
        reviewView.setText(getStarString(rating) + "\n" + reviewText);
        reviewView.setTextSize(16f);
        reviewView.setMaxLines(10);
        reviewView.setSingleLine(false);
        reviewView.setEllipsize(null);
        reviewView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Button deleteButton = new Button(getContext());
        deleteButton.setText("삭제");
        deleteButton.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
        deleteButton.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));

        // 버튼 박스 크기만 줄임 (글씨 크기는 유지)
        int widthInPx = (int) (getResources().getDisplayMetrics().density * 80);  // 80dp
        int heightInPx = (int) (getResources().getDisplayMetrics().density * 40); // 40dp
        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(widthInPx, heightInPx));

        deleteButton.setOnClickListener(delView -> {
            new AlertDialog.Builder(getContext())
                    .setMessage("정말로 삭제하겠습니까?")
                    .setPositiveButton("삭제", (dialog, which) -> {
                        reviewContainer.removeView(reviewItem);
                        boolean removed = false;
                        for (int i = 0; i < reviewList.size(); i++) {
                            Review r = reviewList.get(i);
                            if (r.rating == rating && r.text.equals(reviewText)) {
                                removed = reviewList.remove(r);
                                break;
                            }
                        }
                        if (removed) {
                            Toast.makeText(getContext(), "후기가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "후기 삭제가 실패되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
        });

        textContainer.addView(reviewView);
        reviewItem.addView(textContainer);
        reviewItem.addView(deleteButton);
        reviewContainer.addView(reviewItem);
    }
}