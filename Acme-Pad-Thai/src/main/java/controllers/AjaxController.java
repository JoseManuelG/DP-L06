package controllers;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.RecipeService;
import utilities.RecipeAjaxSearch;
import domain.Recipe;
 
/**
 * @author Crunchify.com
 * 
 */
 
@Controller
public class AjaxController {
 
	@Autowired
	RecipeService recipeService;
	
    @RequestMapping("/ajax")
    public ModelAndView helloAjaxTest() {
        return new ModelAndView("ajax", "message", "ajax.titulo");
    }
    
    @RequestMapping("/loginDisponible.do")
    public @ResponseBody RecipeAjaxSearch loginDisponible(@RequestParam("login") String login) {
    	RecipeAjaxSearch result = new RecipeAjaxSearch();
    	Collection<Recipe> recipes= recipeService.searchForRecipe(login);
    	List<String> ids=new ArrayList<String>();
    	List<String> tickers = new ArrayList<String>();
    	List<String> titles = new ArrayList<String>();
    	List<String> summaries = new ArrayList<String>();
    	List<String> lastModifieds = new ArrayList<String>();
    	for(Recipe r: recipes){
    		ids.add(String.valueOf(r.getId()));
    		tickers.add(r.getTicker());
    		titles.add(r.getTitle());
    		summaries.add(r.getSummary());
    		lastModifieds.add(String.valueOf(r.getLastUpdate()));
    	}
    	result.setIds(ids);
    	result.setTickers(tickers);
    	result.setTitles(titles);
    	result.setSummaries(summaries);
    	result.setLastModifieds(lastModifieds);
        return result;
    }
}