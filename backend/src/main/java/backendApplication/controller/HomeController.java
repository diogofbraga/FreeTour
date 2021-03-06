package backendApplication.controller;

import backendApplication.model.dao.CityService;
import backendApplication.model.dao.TourService;
import backendApplication.model.dao.UserService;
import backendApplication.model.entities.City;
import backendApplication.model.entities.Tour;
import backendApplication.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    CityService cityService;

    @Autowired
    TourService tourService;

   @RequestMapping(value = "/home", method = RequestMethod.GET)
    public Map<String,Object> home() {

        Map<String, Object> r = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Tour> nextTours = new ArrayList<>();
        if(!auth.getName().equals("anonymousUser")) {
            String username = auth.getName();
            User u = userService.get(username);

            nextTours = u.getNextTours(9)
                    .stream().map(s -> { Tour t = (Tour) s.getTour().clone();
                        t.setActive(null);
                        t.setFinished(null);
                        t.setReviews(null);
                        t.setGuide(null);
                        t.setCity( (City) t.getCity().clone());
                        t.getCity().setTours(null);
                        return t;
                    })
                    .collect(Collectors.toList());

        }

        List<City> mostPopularCities = cityService.findMostPopularCities(10);


        List<Tour> suggestedTours = new ArrayList<>();
        if(nextTours.size() == 0){
            for(int i = 0; i<mostPopularCities.size(); i++){
                City c = mostPopularCities.get(i);
                List<Tour> tours = tourService.getRandomActiveTours(c.getName());
                if(tours.size() != 0) {
                    Tour t = tours.get(0);
                    t = (Tour) t.clone();
                    t.setFinished(null);
                    t.setGuide(null);
                    t.setActive(null);
                    t.setFinished(null);
                    t.setReviews(null);
                    t.setCity( (City) t.getCity().clone());
                    t.getCity().setTours(null);
                    suggestedTours.add(t);
                }
                if(suggestedTours.size() >= 9)
                    break;
            }
        }

        for(City c: mostPopularCities.subList(0,6)) {
            c.setTours(null);
            c.clone();
        }

        r.putAll(Collections.singletonMap("mostPopularCities", mostPopularCities.subList(0,6)));
        r.putAll(Collections.singletonMap("nextTours", nextTours));
        r.putAll(Collections.singletonMap("suggestedTours", suggestedTours));

        return r;
   }
}
