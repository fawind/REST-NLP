package api.controllers;

import api.models.lemmatization.Lemmas;
import api.models.response.ResponseWrapper;
import api.models.Words;
import modules.Lemmatizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LemmatizationController {

    private Lemmatizer lemmatizer = new Lemmatizer();

    @RequestMapping(value={"/api/lemmatize"}, method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> getLemmas(@RequestBody Words words) {
        if (words != null && words.getWords() != null) {
            Lemmas lemmas = new Lemmas(lemmatizer.lemmatize(words.getWords()));
            ResponseWrapper<Lemmas> response = new ResponseWrapper<>(lemmas);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new HttpMessageNotReadableException("Invalid arguments");
        }
    }

}
