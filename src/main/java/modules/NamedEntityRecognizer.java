package modules;

import api.models.ner.AnnotatedEntity;
import api.models.ner.AnnotatedText;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.ArrayList;
import java.util.List;

public class NamedEntityRecognizer {

    private final String DEFAULT_NER_THREECLASS_MODEL = "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz";
    private AbstractSequenceClassifier classifier;

    public NamedEntityRecognizer() {
        classifier = CRFClassifier.getClassifierNoExceptions(DEFAULT_NER_THREECLASS_MODEL);
    }

    public AnnotatedText classify(String text) {
        if (text.length() == 0) {
            return new AnnotatedText(text, new ArrayList<AnnotatedEntity>());
        }

        List<List<CoreLabel>> annotatedText = classifier.classify(text);
        List<AnnotatedEntity> entities = findEntities(annotatedText);
        return new AnnotatedText(text, entities);
    }

    public List<AnnotatedEntity> findEntities(List<List<CoreLabel>> classifiedText) {
        List<AnnotatedEntity> entities = new ArrayList<>();
        List<String> currentEntities = new ArrayList<>();
        // Set starting type and offsets
        String oldType = classifiedText.get(0).get(0).get(CoreAnnotations.AnswerAnnotation.class);
        int firstOffsetStart = 0;
        int oldOffsetEnd = 0;

        for (List<CoreLabel> sentence : classifiedText) {
            for (CoreLabel word : sentence) {
                String entity = word.word();
                String type = word.get(CoreAnnotations.AnswerAnnotation.class);
                int beginOffset = word.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class);
                int endOffset = word.get(CoreAnnotations.CharacterOffsetEndAnnotation.class);

                if (oldType.equals(type)) {
                    currentEntities.add(entity);
                    oldOffsetEnd = endOffset;
                } else {
                    if (!oldType.equals("O")) {
                        entities.add(new AnnotatedEntity(String.join(" ", currentEntities), oldType, firstOffsetStart, oldOffsetEnd));
                    }
                    currentEntities = new ArrayList<String>();
                    currentEntities.add(entity);
                    oldType = type;
                    firstOffsetStart = beginOffset;
                    oldOffsetEnd = endOffset;
                }
            }

            if (currentEntities.size() > 0 && !oldType.equals("O")) {
                entities.add(new AnnotatedEntity(String.join(" ", currentEntities), oldType, firstOffsetStart, oldOffsetEnd));
            }
        }

        return entities;
    }

}
