package com.example.po;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ImageButton[] buttons = new ImageButton[8]; // 버튼 배열

    private ArrayList<Integer> imageList; // 이미지 리스트

    private ArrayList<MemoryCard> cards; // 카드 리스트

    private TextView resultText; // 결과 텍스트

    private TextView score; // 점수 텍스트

    private Button resetBtn; // 초기화 버튼

    int preCardPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView score = findViewById(R.id.score);
        resultText = findViewById(R.id.result_text);
        init();

        resetBtn = findViewById(R.id.reset_btn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        }); // onClick
    }

    // 초기화
    public void init() {

        // 리스트에 데이터 등록
        imageList = new ArrayList<>();
        imageList.add(R.drawable.dog);
        imageList.add(R.drawable.pig);
        imageList.add(R.drawable.rabbit);
        imageList.add(R.drawable.cat);
        imageList.add(R.drawable.dog);
        imageList.add(R.drawable.pig);
        imageList.add(R.drawable.rabbit);
        imageList.add(R.drawable.cat);

        // 순서 섞기
        Collections.shuffle(imageList);

        // 카드 리스트 초기화
        cards = new ArrayList<>();

        // 버튼 초기화
        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "imageBtn" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceID);

            // 각 버튼에 클릭 이벤트 적용
            buttons[i].setOnClickListener(this::onClick);
            // 카드 리스트에 이미지 담기
            cards.add(new MemoryCard(imageList.get(i), false, false));
            // 각 버튼에 기본 이미지로 변경
            buttons[i].setImageResource(R.drawable.question);
            // 선명도 설정
            buttons[i].setAlpha(1.0f);
        }
        // 결과 텍스트 초기화
        resultText.setText("");
    }


    //    @Override
    // 버튼 클릭 이벤트
    public void onClick(View view) {
        int id = view.getId();

        int position = 0;

        if (id == R.id.imageBtn0) {
            position = 0;
        } else if (id == R.id.imageBtn1) {
            position = 1;
        } else if (id == R.id.imageBtn2) {
            position = 2;
        } else if (id == R.id.imageBtn3) {
            position = 3;
        } else if (id == R.id.imageBtn4) {
            position = 4;
        } else if (id == R.id.imageBtn5) {
            position = 5;
        } else if (id == R.id.imageBtn6) {
            position = 6;
        } else if (id == R.id.imageBtn7) {
            position = 7;
        }
        // 업데이트 모델
        updateModel(position);
        // 업데이트 뷰
        updateView(position);
    }

    // 데이터 변경
    private void updateModel(int position) {
        MemoryCard card = cards.get(position);
        // 다시 클릭하는거 방지
        if (card.isFaceUp()) {
            Toast.makeText(this, "이미 뒤집혔음", Toast.LENGTH_SHORT).show();
            return;
        }
        if (preCardPosition == -1) {
            restoreCard();
            preCardPosition = position;
        } else {
            checkFormatch(preCardPosition, position);
            preCardPosition = -1;
        }
        cards.get(position).setFaceUp(!card.isFaceUp());
    }

    // 뷰 변경
    private void updateView(int position) {
        MemoryCard card = cards.get(position);
        if (card.isFaceUp()) {
            buttons[position].setImageResource(card.getImageId());
        } else {
            buttons[position].setImageResource(R.drawable.question);
        }
    }

    // 매치되지 않은 카드 초기화
    private void restoreCard() {
        for (int i = 0; i < cards.size(); i++) {
            if (!cards.get(i).isMatched()) {
                buttons[i].setImageResource(R.drawable.question);
                cards.get(i).setFaceUp(false);
            }
        }
    }

    // 카드 이미지 비교
    private void checkFormatch(int prePosition, int position) {

        if (cards.get(prePosition).getImageId() == cards.get(position).getImageId()) {
            resultText.setText("매치 성공");

            cards.get(prePosition).setMatched(true);
            cards.get(position).setMatched(true);

            // 색상 변경
            buttons[prePosition].setAlpha(0.1f);
            buttons[position].setAlpha(0.1f);

            // 완료 체크
            checkCompletion();
        } else {
            resultText.setText("매치 실패");
        }
    }
    // 완료 체크
    private void checkCompletion() {
        int count = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isMatched()) {
                count++;
            }
        }
        if (count == cards.size()) {
            resultText.setText("게임 끝");
        }
    }
}