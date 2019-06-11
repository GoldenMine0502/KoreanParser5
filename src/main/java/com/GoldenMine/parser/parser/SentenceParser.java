package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.Context;
import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.predicate.IPredicate;
import com.GoldenMine.parser.predicate.PredicateStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SentenceParser implements IParser {
    private IParser parser;

    public SentenceParser(IParser parser) {
        this.parser = parser;
    }

    @Override
    public void parse(ParseContext parseContext) {
        if(parser != null)
            parser.parse(parseContext);

        List<Sentence> sentences = parseContext.getSentences();


        HashMap<String, List<Context>> map = new HashMap<>(parseContext.getParsedMap());
        map.entrySet().forEach(it -> it.setValue(new ArrayList<>(it.getValue()))); // copy list
        List<Context> predicates = map.get("서술어");
        for(int predicatesIndex = 0; predicatesIndex < predicates.size(); predicatesIndex++) {
            HashMap<String, Context> subMap = new HashMap<>();

            Context context = predicates.get(predicatesIndex);
            IPredicate predicate = PredicateStorage.INSTANCE.getPredicate(context.getSource());
            List<String> neededs = predicate.getNeededSentenceElements();
            List<String> selectables = predicate.getOptionalSentenceElements();

            int minimumStartRange = context.getPosStart();

            for(int neededsIndex = 0; neededsIndex < neededs.size(); neededsIndex++) {
                String needed = neededs.get(neededsIndex);
                List<Context> elementList = map.get(needed);
                if(elementList != null && elementList.size() > 0) {
                    for(int elementIndex = elementList.size() - 1; elementIndex >= 0; elementIndex--) {
                        Context element = elementList.get(elementIndex);

                        if(element.getPosStart() < context.getPosStart()) {
                            if (minimumStartRange > element.getPosStart())
                                minimumStartRange = element.getPosStart();

                            subMap.put(needed, element);
                            elementList.remove(elementIndex);
                            break;
                        }
                    }
                } else {
                    throw new RuntimeException("해당하는 문장 성분이 없습니다: " + needed + ", " + predicate.getDefaultSentence() + ", " + map);
                }
            }

            for(int selectablesIndex = 0; selectablesIndex < selectables.size(); selectablesIndex++) {
                String selectable = selectables.get(selectablesIndex);
                List<Context> elementList = map.get(selectable);
                Context element = elementList.get(elementList.size() - 1);

                if(minimumStartRange < element.getPosStart()) {
                    subMap.put(selectable, element);
                }
            }

            sentences.add(new Sentence(subMap, predicate));
        }
    }
}
