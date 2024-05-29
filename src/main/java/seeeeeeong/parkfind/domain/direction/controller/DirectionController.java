package seeeeeeong.parkfind.domain.direction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import seeeeeeong.parkfind.domain.direction.service.DirectionService;

@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId) {

        String result = directionService.findDirectionUrlById(encodedId);

        return "redirect:"+result;

    }
}
