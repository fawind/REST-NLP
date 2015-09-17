package api.controllers;

import api.models.Text;
import api.models.Tokens;
import modules.Tokenizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TokenizerController {

    private Tokenizer tokenizer = new Tokenizer();

    @RequestMapping(value="/api/tokenize", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Tokens> getTokens(@RequestBody Text text) {
        if (text != null) {
            List<String> tokenList = tokenizer.tokenizeWords(text.getText());
            Tokens tokens = new Tokens(text.getText(), tokenList);
            return new ResponseEntity<Tokens>(tokens, HttpStatus.OK);
        } else {
            Tokens tokens = new Tokens(text.getText(), new ArrayList());
            return new ResponseEntity<Tokens>(tokens, HttpStatus.OK);
        }
    }

    @RequestMapping(value="/api/tokenize/words", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Tokens> getWords(@RequestBody Text text) {
        if (text != null) {
            List<String> tokenList = tokenizer.tokenizeSentences(text.getText());
            Tokens tokens = new Tokens(text.getText(), tokenList);
            return new ResponseEntity<Tokens>(tokens, HttpStatus.OK);
        } else {
            Tokens tokens = new Tokens(text.getText(), new ArrayList());
            return new ResponseEntity<Tokens>(tokens, HttpStatus.OK);
        }
    }
}
