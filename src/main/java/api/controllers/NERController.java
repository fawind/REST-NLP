package api.controllers;

import api.models.Text;
import api.models.ner.AnnotatedText;
import api.models.response.ResponseWrapper;
import modules.NamedEntityRecognizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class NERController {

    private NamedEntityRecognizer ner = new NamedEntityRecognizer();

    @RequestMapping(value={"/api/ner"}, method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> getTokens(@RequestBody Text text) {
        if (text != null && text.getText() != null) {
            AnnotatedText annotatedText = ner.classify(text.getText());
            ResponseWrapper<AnnotatedText> response = new ResponseWrapper<>(annotatedText);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new HttpMessageNotReadableException("Invalid arguments");
        }
    }

    @RequestMapping(value={"/api/ner/german"}, method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> getGermanTokens(@RequestBody Text text) {
        if (text != null && text.getText() != null) {
            AnnotatedText annotatedText = ner.classifyGerman(text.getText());
            ResponseWrapper<AnnotatedText> response = new ResponseWrapper<>(annotatedText);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new HttpMessageNotReadableException("Invalid arguments");
        }
    }

}
