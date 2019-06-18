package com.GoldenMine.object;

import com.GoldenMine.parser.Variable;

public class Point2DCreator implements KoreanObjectCreator {
    @Override
    public String getType() {
        return "xy좌표";
    }

    @Override
    public Variable.VariableMode getValueType(String key) {
        switch(key) {
            case "x":
            case "X":
            case "x좌표":
            case "X좌표":
                return Variable.VariableMode.REALNUM_MODE;
            case "y":
            case "Y":
            case "y좌표":
            case "Y좌표":
                return Variable.VariableMode.REALNUM_MODE;
        }

        return Variable.VariableMode.NULL;
    }

    @Override
    public String getRegex() {
        return "[d], [d]";
    }

    @Override
    public KoreanObject getInstance() {
        return new Point2D();
    }
}
