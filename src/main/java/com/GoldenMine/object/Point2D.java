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
    public Object getValue(String key) {
        switch (key) {
            case "x":
            case "X":
                return x;
            case "y":
            case "Y":
                return y;
        }
        return null;
    }

    @Override
    public void setValue(String key, Object value) {
        switch (key) {
            case "x":
            case "X":
                this.x = (double) value;
                break;
            case "y":
            case "Y":
                this.y = (double) value;
                break;
        }
    }

    @Override
    public void setValue(int index, Object value) {
        switch (index) {
            case 0:
                this.x = (double) value;
                break;
            case 1:
                this.y = (double) value;
                break;
        }
    }

    @Override
    public int compareTo(@NotNull KoreanObject o) {
        if(o instanceof Point2D) {
            Point2D p2d = (Point2D) o;

            if (x < p2d.x) {
                return -1;
            } else if (x > p2d.x) {
                return 1;
            } else if (y < p2d.y) {
                return -1;
            } else if (y > p2d.y) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
