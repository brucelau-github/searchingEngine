/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author Jianye Liu brucelau.email@gmail.com
 */
package query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QueryTrieController {
	
	@Autowired
	private QueryTrieService queryTrieService;
   
	@RequestMapping("/querytrie")
    public String querytrie(@RequestParam(value="keyword", required=false, defaultValue="the") String keyword, Model model) {
		List<QueryItem> itemsList = queryTrieService.getQueryReslt(keyword.trim().toLowerCase());
		if(itemsList.isEmpty()) model.addAttribute("NoItems","No result found!");
		else model.addAttribute("itemsList",itemsList);
        return "query";
    }

}