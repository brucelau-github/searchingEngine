package query;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryKeyController {

	@Autowired
	private QueryKeysService keyService;
	
    @RequestMapping("/querykeys1")
    public KeyItem[] getKeys(@RequestParam(value="term", defaultValue="World") String term) {
    	//item json return 
        return keyService.queryKeys(term);
    }
    @RequestMapping("/querykey")
    public String[] getStringKey(@RequestParam(value="term", defaultValue="World") String term) {
    	
        return keyService.queryKeysWithString(term.trim());
    }
    @RequestMapping("/querykeys")
    public String[] getStringKeys(@RequestParam(value="term", defaultValue="World") String term) {
    	//with many key words
        return keyService.queryManyKeys(term.trim());
    }
    @RequestMapping("/querytriekeys")
    public String[] gettrieKeys(@RequestParam(value="term", defaultValue="World") String term) {
    	//with many key words
        return keyService.queryManyKeysTire(term.trim());
    }
}