package api.controllers;

import api.models.Lemmas;
import api.models.Text;
import api.models.Tokens;
import api.models.response.ResponseWrapper;
import modules.Lemmatizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LemmatizationController {

    private Lemmatizer lemmatizer = new Lemmatizer();

    @RequestMapping(value={"/api/lemmatize"}, method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> getTokens(@RequestBody Text text) {
        if (text != null && text.getText() != null) {
            Lemmas lemmas = new Lemmas(text.getText(), lemmatizer.lemmatize(text.getText()));
            ResponseWrapper<Lemmas> response = new ResponseWrapper<Lemmas>(lemmas);
            return new ResponseEntity<ResponseWrapper>(response, HttpStatus.OK);
        } else {
            throw new HttpMessageNotReadableException("Invalid arguments");
        }
    }


}
