package net.moltak.heartbeathue.logic.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by moltak on 12/17/15.
 */
public class ColorBlindnessReferee {
    private final List<Integer> CORRECT_LIST = Arrays.asList(1, 0, 2, 0, 0, 2, 1, 0, 1);
    private List<Integer> choiceList = new ArrayList<>();

    public void choice(int choice) {
        choiceList.add(choice);
    }

    public ColorBlindType getTestResult() {
        for (int i : choiceList) {
            Log.d("stage", String.valueOf(i));
        }

        // 전색맹 구분
        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (choiceList.get(i).equals(CORRECT_LIST.get(i))) {
                score++;
            }
        }
        if (score < 3) {
            // 전생맹
            return ColorBlindType.ACHROMAOPSIA;
        }

        score = 0;
        for (int i = 4; i < 8; i++) {
            if (choiceList.get(i).equals(CORRECT_LIST.get(i))) {
                score++;
            }
        }
        if (score > 2) {
            // 정상
            return ColorBlindType.NORMAL;
        }

        int lastQuizIndex = choiceList.size() - 1;
        if (choiceList.get(lastQuizIndex).equals(CORRECT_LIST.get(lastQuizIndex))) {
            // 제2색맹 : 녹색맹
            return ColorBlindType.DEUTERANOPIA;
        }
        // 제1색맹 : 적색맹
        return ColorBlindType.PROTANOPIA;
    }

    public enum ColorBlindType {
        // 제1색맹 : 적색맹
        PROTANOPIA,
        // 제1색약 : 적색약
        PROTANOMALY,
        // 제2색맹 : 녹색맹
        DEUTERANOPIA,
        // 제2색약 : 녹색약
        DEUTERANOMALY,
        // 제3색맹 : 청황색맹
        TRITANOPIA,
        // 제3색약 : 청색약
        TRITANOMALY,
        // 전색맹
        ACHROMAOPSIA,
        // 전색약
        ACHROAMTOMALY,
        // 정상
        NORMAL
    }
}
