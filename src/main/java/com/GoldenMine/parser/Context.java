package com.GoldenMine.parser;

import java.util.List;

public class Context {
    private String source;
    private List<Variable> variable;
    private int posStart;
    private int posFinish;

    private boolean isConst;

    public Context(String str, boolean isConst, int posStart, int posFinish) {
        this.source = str;
        this.isConst = isConst;
        this.posStart = posStart;
        this.posFinish = posFinish;
    }

    public void setSource(String source) {
        if(!isConst) {
            this.source = source;
        }
    }

    public void setVariable(List<Variable> variable) {
        this.variable = variable;
    }

    public void setConst() {
        isConst = true;
    }

    public int getPosStart() {
        return posStart;
    }

    public int getPosFinish() {
        return posFinish;
    }

    @Override
    public String toString() {
        return (variable != null ? variable.toString() + ", size: " + variable.size() : source) + ", (" + posStart + ", " + posFinish + ")";
    }

    public String getSource() {
        return source;
    }

    public List<Variable> getVariables() {
        return variable;
    }

    public static Variable parseVariable(String str) {
        if(str.startsWith("\"") && str.endsWith("\"") || str.startsWith("\'") && str.endsWith("\'")) {
            return new Variable(str.substring(1, str.length() - 1), false);
        }
        if(isBoolean(str)) {
            return new Variable(Boolean.parseBoolean(str), false);
        }
        if (isInteger(str)) {
            return new Variable(Integer.parseInt(str), false);
        }
        if(isDouble(str)) {
            return new Variable(Double.parseDouble(str), false);
        }

        return new Variable(str, false);
    }

    public static boolean isBoolean(String str) {
        String toLower = str.toLowerCase();

        return toLower.equals("true") || toLower.equals("false");
    }

    public static boolean isDouble(String str) {
//        boolean dotCounted = false;
//
//        for(int i = 0; i < str.length(); i++) {
//            char ch = str.charAt(i);
//            if('9' < ch || ch < '0') {
//                if(ch == '.') {
//                    if(dotCounted) {
//                        return false;
//                    }
//                    dotCounted = true;
//                }
//                return false;
//            }
//        }
//
//        return true;
        try {
            Double.parseDouble(str);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(Exception ex) {
            return false;
        }
//        for(int i = 0; i < str.length(); i++) {
//            char ch = str.charAt(i);
//            if('9' < ch || ch < '0') {
//                return false;
//            }
//        }
//
//        return true;
    }
}
