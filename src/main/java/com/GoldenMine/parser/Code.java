package com.GoldenMine.parser;

import com.GoldenMine.parser.parser.IParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Code {
    private List<ParseContext> sourceCode;

    public Code(List<String> sources) {
        sourceCode = new ArrayList<>();

        for(int i = 0; i < sources.size(); i++) {
            sourceCode.add(new ParseContext(sources.get(i)));
        }
    }

    public List<ParseContext> getSourceCode() {
        return sourceCode;
    }
}
