package seeeeeeong.parkfind.domain.park.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import seeeeeeong.parkfind.domain.park.cache.ParkRedisTemplateService;
import seeeeeeong.parkfind.domain.park.dto.ParkDto;
import seeeeeeong.parkfind.domain.park.service.ParkRepositoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ParkController {

    private final ParkRepositoryService parkRepositoryService;
    private final ParkRedisTemplateService parkRedisTemplateService;

    @GetMapping("/redis/save")
    public String save() {

        List<ParkDto> parkDtoList = parkRepositoryService.findAll()
                .stream().map(park -> ParkDto.builder()
                        .id(park.getId())
                        .parkName(park.getParkName())
                        .parkAddress(park.getParkAddress())
                        .latitude(park.getLatitude())
                        .longitude(park.getLongitude())
                        .build())
                .collect(Collectors.toList());

        parkDtoList.forEach(parkRedisTemplateService::save);

        return "success";
    }
}
