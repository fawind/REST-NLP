package modules;

import api.models.ner.AnnotatedEntity;
import api.models.ner.AnnotatedText;
import api.models.ner.Offset;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.ArrayList;
import java.util.List;

public class NamedEntityRecognizer {

    private final String DEFAULT_NER_THREECLASS_MODEL = "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz";
    private final String GERMAN_NER_THREECLASS_MODEL = "edu/stanford/nlp/models/ner/german.dewac_175m_600.crf.ser.gz";
    private AbstractSequenceClassifier classifier;
    private AbstractSequenceClassifier germanClassifier;

    public NamedEntityRecognizer() {
        classifier = CRFClassifier.getClassifierNoExceptions(DEFAULT_NER_THREECLASS_MODEL);
        germanClassifier = CRFClassifier.getClassifierNoExceptions(GERMAN_NER_THREECLASS_MODEL);
    }

    public AnnotatedText classify(String text) {
        return findEntities(text, classifier);
    }

    public AnnotatedText classifyGerman(String text) {
        return findEntities(text, germanClassifier);
    }

    private AnnotatedText findEntities(String text, AbstractSequenceClassifier classifier) {
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
                        Offset offset = new Offset(firstOffsetStart, oldOffsetEnd);
                        entities.add(new AnnotatedEntity(String.join(" ", currentEntities), oldType, offset));
                    }
                    currentEntities = new ArrayList<String>();
                    currentEntities.add(entity);
                    oldType = type;
                    firstOffsetStart = beginOffset;
                    oldOffsetEnd = endOffset;
                }
            }

            if (currentEntities.size() > 0 && !oldType.equals("O")) {
                Offset offset = new Offset(firstOffsetStart, oldOffsetEnd);
                entities.add(new AnnotatedEntity(String.join(" ", currentEntities), oldType, offset));
            }
        }

        return mergeEntities(entities);
    }

    public List<AnnotatedEntity> mergeEntities(List<AnnotatedEntity> entities) {
        List<AnnotatedEntity> mergedEntities = new ArrayList<>();
        boolean merge;

        for (AnnotatedEntity entity : entities) {
            merge = false;
            for (AnnotatedEntity mergedEntity : mergedEntities) {
                if (entity.getEntity().equals(mergedEntity.getEntity())) {
                    for (Offset offset : entity.getOffsets()) {
                        mergedEntity.addOffset(offset);
                    }

                    merge = true;
                }
            }

            if (!merge) {
                mergedEntities.add(entity);
            }
        }

        return mergedEntities;
    }

}
