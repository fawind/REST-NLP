package api.controllers;

import api.models.response.ResponseWrapper;
import api.models.Text;
import api.models.tokenizer.Tokens;
import modules.Tokenizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TokenizerController {

    private Tokenizer tokenizer = new Tokenizer();

    @RequestMapping(value={"/api/tokenize", "/api/tokenize/tokens"}, method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> getTokens(@RequestBody Text text) {
        if (text != null && text.getText() != null) {
            List<String> tokenList = tokenizer.tokenizeWords(text.getText());
            Tokens tokens = new Tokens(text.getText(), tokenList);
            ResponseWrapper<Tokens> response = new ResponseWrapper<>(tokens);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new HttpMessageNotReadableException("Invalid arguments");
        }
    }

    @RequestMapping(value="/api/tokenize/words", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> getWords(@RequestBody Text text) {
        if (text != null  && text.getText() != null) {
            List<String> tokenList = tokenizer.tokenizeSentences(text.getText());
            Tokens tokens = new Tokens(text.getText(), tokenList);
            ResponseWrapper<Tokens> response = new ResponseWrapper<>(tokens);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new HttpMessageNotReadableException("Invalid arguments");
        }
    }
}
