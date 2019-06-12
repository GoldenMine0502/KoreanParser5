package com.GoldenMine.object;

import com.GoldenMine.parser.Variable;
import org.jetbrains.annotations.NotNull;

public class Point2D implements KoreanObject {
    double x;
    double y;

    // a와 b로 좌표를 만듭니다.
    // new Point2D(x, y);
    // 10과 30으로 xy좌표를 만듭니다.

    @Override
    public String getType() {
        return "좌표";
    }

    @Override
    public Variable.VariableMode getValueType(String key) {
        switch(key) {
            case "x":
            case "X":
                return Variable.VariableMode.REALNUM_MODE;
        }

        return Variable.VariableMode.NULL;
    }

    @Override
    public Object getValue(String key) {
        return null;
    }

    @Override
    public void setValue(String key, Object value) {

    }

    @Override
    public void setValue(int index, Object value) {

    }

    @Override
    public int compareTo(@NotNull KoreanObject o) {
        return 0;
    }
}
