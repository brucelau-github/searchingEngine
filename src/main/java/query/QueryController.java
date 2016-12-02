package query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QueryController {
	
	@Autowired
	private QueryService queryService;
   
	@RequestMapping("/query")
    public String greeting(@RequestParam(value="keyword", required=false, defaultValue="the") String keyword, Model model) {
		List<QueryItem> itemsList = queryService.getQueryReslt(keyword.trim().toLowerCase());
		if(itemsList.isEmpty()) model.addAttribute("NoItems","No result found!");
		else model.addAttribute("itemsList",itemsList);
        return "query";
    }

}