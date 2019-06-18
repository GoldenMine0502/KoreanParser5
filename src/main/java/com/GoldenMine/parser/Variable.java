package com.GoldenMine.parser;

import com.GoldenMine.impl.CalculateException;
import com.GoldenMine.impl.ConstantException;

public class Variable {


    public enum VariableMode {
        NULL(-1),
        BOOLEAN_MODE(0),
        INT_MODE(1),
        REALNUM_MODE(2),
        STRING_MODE(3),
        OBJECT_MODE(4);

        int mode;

        VariableMode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }

        public static VariableMode get(int mode) {
            switch (mode) {
                case 0:
                    return BOOLEAN_MODE;
                case 1:
                    return INT_MODE;
                case 2:
                    return REALNUM_MODE;
                case 3:
                    return STRING_MODE;
                case 4:
                    return OBJECT_MODE;
            }

            return null;
        }
    }

    private boolean booleanValue;
    private long intValue;
    private double realNumValue;
    private String stringValue;
    private Comparable objectValue;

    private boolean isConst;

    private VariableMode mode;


    public Variable(VariableMode mode, boolean isConst) {
        this.mode = mode;
        this.isConst = isConst;
    }

//    public Variable(VariableMode mode, Object data) {
//        this.mode = mode;
//        set(data);
//    }

    public Variable(long value, boolean isConst) {
        intValue = value;
        mode = VariableMode.INT_MODE;

        this.isConst = isConst;
    }

    public Variable(double value, boolean isConst) {
        realNumValue = value;
        mode = VariableMode.REALNUM_MODE;

        this.isConst = isConst;
    }

    public Variable(boolean value, boolean isConst) {
        booleanValue = value;
        mode = VariableMode.BOOLEAN_MODE;

        this.isConst = isConst;
    }

    public Variable(String value, boolean isConst) {
        stringValue = value;
        mode = VariableMode.STRING_MODE;

        this.isConst = isConst;
    }

    public void setConst() {
        isConst = true;
    }

    public Object get() {
        switch (mode) {
            case BOOLEAN_MODE:
                return booleanValue();
            case INT_MODE:
                return intValue();
            case REALNUM_MODE:
                return realNumValue();
            case STRING_MODE:
                return stringValue();
            case OBJECT_MODE:
                return objectValue();
        }
        return null;
    }

    public boolean toBoolean() {
        switch (getMode()) {
            case BOOLEAN_MODE:
                return booleanValue();
            case INT_MODE:
                return intValue() != 0;
            case REALNUM_MODE:
                return realNumValue() != 0;
            case STRING_MODE:
                throw new CalculateException("String을 boolean으로 바꿀 수 없습니다. 0001");
            case OBJECT_MODE:
                throw new CalculateException("Object를 boolean으로 바꿀 수 없습니다.");
        }
        throw new CalculateException("boolean으로 바꿀 수 없습니다. 0000");
    }

    public boolean OR(Variable variable) {
        switch (getMode()) {
            case BOOLEAN_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return booleanValue() || variable.booleanValue();
                    case INT_MODE:
                        return booleanValue() || (variable.intValue() != 0);
                    case REALNUM_MODE:
                        return booleanValue() || (variable.realNumValue() != 0);
                    case STRING_MODE:
                        throw new CalculateException("OR에서 String 연산은 불가합니다.");
                    case OBJECT_MODE:
                        throw new CalculateException("OR에서 Object 연산은 불가합니다.");
                }
                break;
            case INT_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return (intValue() != 0) || variable.booleanValue();
                    case INT_MODE:
                        return (intValue() != 0) || (variable.intValue() != 0);
                    case REALNUM_MODE:
                        return (intValue() != 0) || (variable.realNumValue() != 0);
                    case STRING_MODE:
                        throw new CalculateException("OR에서 String 연산은 불가합니다.");
                    case OBJECT_MODE:
                        throw new CalculateException("OR에서 Object 연산은 불가합니다.");
                }
                break;
            case REALNUM_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return (realNumValue() != 0) || variable.booleanValue();
                    case INT_MODE:
                        return (realNumValue() != 0) || (variable.intValue() != 0);
                    case REALNUM_MODE:
                        return (realNumValue() != 0) || (variable.realNumValue() != 0);
                    case STRING_MODE:
                        throw new CalculateException("OR에서 String 연산은 불가합니다.");
                    case OBJECT_MODE:
                        throw new CalculateException("OR에서 Object 연산은 불가합니다.");
                }
                break;
            case STRING_MODE:
                throw new CalculateException("OR에서 String 연산은 불가합니다.");
            case OBJECT_MODE:
                throw new CalculateException("OR에서 Object 연산은 불가합니다.");
        }
        throw new CalculateException("알 수 없는 이유로 OR연산을 진행할 수 없습니다.");
    }

    public boolean AND(Variable variable) {
        switch (getMode()) {
            case BOOLEAN_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return booleanValue() && variable.booleanValue();
                    case INT_MODE:
                        return booleanValue() && (variable.intValue() != 0);
                    case REALNUM_MODE:
                        return booleanValue() && (variable.realNumValue() != 0);
                    case STRING_MODE:
                        throw new CalculateException("AND에서 String 연산은 불가합니다. 0002");
                    case OBJECT_MODE:
                        throw new CalculateException("AND에서 Object 연산은 불가합니다.");
                }
                break;
            case INT_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return (intValue() != 0) && variable.booleanValue();
                    case INT_MODE:
                        return (intValue() != 0) && (variable.intValue() != 0);
                    case REALNUM_MODE:
                        return (intValue() != 0) && (variable.realNumValue() != 0);
                    case STRING_MODE:
                        throw new CalculateException("AND에서 String 연산은 불가합니다. 0002");
                    case OBJECT_MODE:
                        throw new CalculateException("AND에서 Object 연산은 불가합니다.");
                }
                break;
            case REALNUM_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return (realNumValue() != 0) || variable.booleanValue();
                    case INT_MODE:
                        return (realNumValue() != 0) || (variable.realNumValue() != 0);
                    case REALNUM_MODE:
                        return (realNumValue() != 0) || (variable.realNumValue() != 0);
                    case STRING_MODE:
                        throw new CalculateException("AND에서 String 연산은 불가합니다. 0002");
                    case OBJECT_MODE:
                        throw new CalculateException("AND에서 Object 연산은 불가합니다.");
                }
                break;
            case STRING_MODE:
                throw new CalculateException("AND에서 String 연산은 불가합니다. 0001");
            case OBJECT_MODE:
                throw new CalculateException("AND에서 Object 연산은 불가합니다.");
        }
        throw new CalculateException("알 수 없는 이유로 AND연산을 진행할 수 없습니다. 0000");
    }

    public boolean equals(Variable variable) {
        switch (getMode()) {
            case BOOLEAN_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return booleanValue() == variable.booleanValue();
                    case INT_MODE:
                        return booleanValue() == (variable.intValue() != 0);
                    case REALNUM_MODE:
                        return booleanValue() == (variable.realNumValue() != 0);
                    case STRING_MODE:
                        return false;
                    case OBJECT_MODE:
                        return false;
                }
                break;
            case INT_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return (intValue() != 0) == variable.booleanValue();
                    case INT_MODE:
                        return intValue() == variable.intValue();
                    case REALNUM_MODE:
                        return intValue() == variable.realNumValue();
                    case STRING_MODE:
                        return false;
                    case OBJECT_MODE:
                        return false;
                }
                break;
            case REALNUM_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return (realNumValue() != 0) == variable.booleanValue();
                    case INT_MODE:
                        return realNumValue() == variable.intValue();
                    case REALNUM_MODE:
                        return realNumValue() == variable.realNumValue();
                    case STRING_MODE:
                        return false;
                    case OBJECT_MODE:
                        return false;
                }
                break;
            case STRING_MODE:
                switch (variable.getMode()) {

                    case BOOLEAN_MODE:
                        return false;
                    case INT_MODE:
                        return false;
                    case REALNUM_MODE:
                        return false;
                    case STRING_MODE:
                        return stringValue().equals(variable.stringValue());
                    case OBJECT_MODE:
                        return false;
                }
                break;

            case OBJECT_MODE:
                switch (variable.getMode()) {

                    case BOOLEAN_MODE:
                        return false;
                    case INT_MODE:
                        return false;
                    case REALNUM_MODE:
                        return false;
                    case STRING_MODE:
                        return false;
                    case OBJECT_MODE:
                        return variable.objectValue().equals(objectValue());
                }
                break;
        }

        return false;
    }

    @Override
    public Variable clone() throws CloneNotSupportedException {
        Variable variable = (Variable) super.clone();

        return variable;
    }

    public boolean smallerThan(Variable variable) {
        switch (getMode()) {
            case BOOLEAN_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return !booleanValue() && variable.booleanValue();
                    case INT_MODE:
                        if (booleanValue()) {
                            return variable.intValue() > 1;
                        } else {
                            return variable.intValue() > 0;
                        }
                    case REALNUM_MODE:
                        if (booleanValue()) {
                            return variable.realNumValue() > 1;
                        } else {
                            return variable.realNumValue() > 0;
                        }
                }
                break;
            case INT_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        if (variable.booleanValue()) {
                            return intValue() < 1;
                        } else {
                            return intValue() < 0;
                        }
                    case INT_MODE:
                        return intValue() < variable.intValue();
                    case REALNUM_MODE:
                        return intValue() < variable.realNumValue();
                }
                break;
            case REALNUM_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        if (variable.booleanValue()) {
                            return realNumValue() < 1;
                        } else {
                            return realNumValue() < 0;
                        }
                    case INT_MODE:
                        return realNumValue() < variable.intValue();
                    case REALNUM_MODE:
                        return realNumValue() < variable.realNumValue();
                }
                break;
            case STRING_MODE:
                switch (variable.getMode()) {
                    case STRING_MODE:
                        return stringValue().compareTo(variable.stringValue()) > 0;
                }
                break;
            case OBJECT_MODE:
                switch (variable.getMode()) {
                    case OBJECT_MODE:
                        return objectValue.compareTo(variable.objectValue) > 0;
                }
                break;
        }

        return false;
    }


    public boolean isTrue() {
        switch(getMode()) {
            case NULL:
                return false;
            case BOOLEAN_MODE:
                return booleanValue;
            case INT_MODE:
                return intValue==1;
            case REALNUM_MODE:
                return realNumValue==1;
            case STRING_MODE:
                return false;
            case OBJECT_MODE:
                return false;
        }

        return false;
    }



    public boolean biggerThan(Variable variable) {
        switch (getMode()) {
            case BOOLEAN_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        return booleanValue() && !variable.booleanValue();
                    case INT_MODE:
                        if (booleanValue()) {
                            return variable.intValue() < 1;
                        } else {
                            return variable.intValue() < 0;
                        }
                    case REALNUM_MODE:
                        if (booleanValue()) {
                            return variable.realNumValue() < 1;
                        } else {
                            return variable.realNumValue() < 0;
                        }
                    case STRING_MODE:
                        return false;
                }
                break;
            case INT_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        if (variable.booleanValue()) {
                            return intValue() > 1;
                        } else {
                            return intValue() > 0;
                        }
                    case INT_MODE:
                        return intValue() > variable.intValue();
                    case REALNUM_MODE:
                        return intValue() > variable.realNumValue();
                }
                break;
            case REALNUM_MODE:
                switch (variable.getMode()) {
                    case BOOLEAN_MODE:
                        if (variable.booleanValue()) {
                            return realNumValue() > 1;
                        } else {
                            return realNumValue() > 0;
                        }
                    case INT_MODE:
                        return realNumValue() > variable.intValue();
                    case REALNUM_MODE:
                        return realNumValue() > variable.realNumValue();
                }
                break;
            case STRING_MODE:
                switch (variable.getMode()) {
                    case STRING_MODE:
                        return stringValue().compareTo(variable.stringValue()) < 0;
                }
                break;
            case OBJECT_MODE:
                switch (variable.getMode()) {
                    case OBJECT_MODE:
                        return objectValue.compareTo(variable.objectValue) < 0;
                }
                break;
        }

        return false;
    }

    public void set(Object obj) {
        if (!isConst) {
            switch (mode) {
                case BOOLEAN_MODE:
                    booleanValue = (boolean) obj;
                    break;
                case INT_MODE:
                    intValue = (long) obj;
                    break;
                case REALNUM_MODE:
                    realNumValue = (double) obj;
                    break;
                case STRING_MODE:
                    stringValue = (String) obj;
                    break;
                case OBJECT_MODE:
                    objectValue = (Comparable) obj;
            }
        } else {
            throw new ConstantException("상수 변수입니다.");
        }
    }

    public void castCompelNoMaintain(VariableMode mode) {
        this.mode = mode;
    }

    public void castCompel(VariableMode toMode) {
        if (mode.getMode() != toMode.getMode()) {
            switch (mode) {
                case INT_MODE:
                    switch (toMode) {
                        case BOOLEAN_MODE:
                            booleanValue = (intValue & 0x1) == 1;
                            break;
                        case REALNUM_MODE:
                            realNumValue = intValue;
                            break;
                        case STRING_MODE:
                            stringValue = String.valueOf(intValue);
                            break;
                    }
                    break;
                case BOOLEAN_MODE:
                    switch (toMode) {
                        case INT_MODE:
                            intValue = booleanValue ? 1 : 0;
                            break;
                        case REALNUM_MODE:
                            realNumValue = booleanValue ? 1 : 0;
                            break;
                        case STRING_MODE:
                            stringValue = String.valueOf(booleanValue);
                            break;
                    }
                    break;
                case REALNUM_MODE:
                    switch (toMode) {
                        case BOOLEAN_MODE:
                            booleanValue = ((int) realNumValue & 0x1) == 1;
                            break;
                        case INT_MODE:
                            intValue = (long) realNumValue;
                            break;
                        case STRING_MODE:
                            stringValue = String.valueOf(realNumValue);
                            break;
                    }
                    break;
                case STRING_MODE:
                    switch (toMode) {
                        case BOOLEAN_MODE:
                            booleanValue = Boolean.parseBoolean(stringValue);
                            break;
                        case INT_MODE:
                            intValue = Long.parseLong(stringValue);
                            break;
                        case REALNUM_MODE:
                            realNumValue = Double.parseDouble(stringValue);
                            break;
                    }
            }

            mode = toMode;
        }
    }

    public void cast(VariableMode toMode) {
        if (mode.getMode() < toMode.getMode()) {
            castCompel(toMode);
        }
    }

    public VariableMode getMode() {
        return mode;
    }

    public long intValue() {
        return intValue;
    }

    public double realNumValue() {
        return realNumValue;
    }

    public String stringValue() {
        return stringValue;
    }

    public boolean booleanValue() {
        return booleanValue;
    }

    public Object objectValue() {
        return objectValue;
    }

//    public Object value() {
//        switch(mode) {
//            case INT_MODE:
//                return intValue();
//            case REALNUM_MODE:
//                return realNumValue();
//            case STRING_MODE:
//                return stringValue();
//            case BOOLEAN_MODE:
//                return booleanValue();
//        }
//
//        return null;
//    }

    public String toString() {
        return mode + ", " + get();
    }
//
//    public static Variable initVariable(KoreanProgramVariableStorage variableStorage, Element value, Element valueType) {
//        int valueTypeInt = (int) valueType.getRootLong();
//        //System.out.println(value + ", " + valueTypeInt + ", " + valueType);
//
//        if(valueTypeInt!= VariableVerify.IS_VARIABLE) {
//
//            Variable tempVariable = null;
//
//            switch(Variable.VariableMode.get(valueTypeInt)) {
//                case BOOLEAN_MODE:
//                    tempVariable = new Variable(Variable.VariableMode.BOOLEAN_MODE, value.getRootBoolean());
//                    break;
//                case INT_MODE:
//                    tempVariable = new Variable(Variable.VariableMode.INT_MODE, value.getRootLong());
//                    break;
//                case REALNUM_MODE:
//                    tempVariable = new Variable(Variable.VariableMode.REALNUM_MODE, value.getRootDouble());
//                    break;
//                case STRING_MODE:
//                    tempVariable = new Variable(Variable.VariableMode.STRING_MODE, value.getRoot());
//                    break;
//            }
//
//            return tempVariable;
//        } else {
//            Variable variable = variableStorage.getVariables(value.getRoot());
//
//            return variable;
//        }
//    }
//
//
//    public static String processVariable(KoreanProgramVariableStorage storage, String source) {
//        StringBuilder sb = new StringBuilder(source);
//        processVariable(sb, 0, false, storage);
//
//        return sb.toString();
//    }
//
//    public static int processVariable(StringBuilder sb, int pos, boolean inner, KoreanProgramVariableStorage storage) {
//        int start = pos;
//
//        while (pos < sb.length()) {
//            char ch = sb.charAt(pos);
//            ++pos;
//            if (ch == '<')
//                pos = processVariable(sb, pos, true, storage);
//            else if (ch == '>')
//                break;
//        }
//
//        if (inner) {
//            try {
//                String key = sb.substring(start, pos - 1);
//                String val = storage.getVariables(key).valueToStr();
//
//                if (val != null) {
//                    sb.delete(start - 1, pos);
//                    sb.insert(start - 1, val);
//
//                    pos = start - 1 + val.length();
//                }
//            } catch(Exception ex) {
//                //System.out.println(sb.substring(start, pos - 1));
//                //System.out.println(storage.getVariables(sb.substring(start, pos - 1)).get());
//            }
//        }
//        // System.out.println(sb.toString());
//        return pos;
//    }
}
