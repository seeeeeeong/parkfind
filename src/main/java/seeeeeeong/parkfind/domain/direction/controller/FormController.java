package seeeeeeong.parkfind.domain.direction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import seeeeeeong.parkfind.domain.direction.dto.InputDto;
import seeeeeeong.parkfind.domain.park.service.ParkRecommendationService;

@Controller
@RequiredArgsConstructor
public class FormController {

    private final ParkRecommendationService parkRecommendationService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                parkRecommendationService.recommendParkList(inputDto.getAddress()));

        return modelAndView;
    }
}
