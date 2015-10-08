package modules;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {


    public List<String> tokenizeWords(String inputText) {
        PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new StringReader(inputText), new CoreLabelTokenFactory(), "");

        List<String> tokens = new ArrayList<>();
        while (ptbt.hasNext()) {
            CoreLabel label = ptbt.next();
            tokens.add(label.toString());
        }

        return tokens;
    }

    public List<String> tokenizeSentences(String inputText) {
        DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(inputText));

        List<String> tokens = new ArrayList<>();
        for (List<HasWord> sentence : dp) {
            tokens.add(sentence.toString());
        }

        return tokens;
    }
}
