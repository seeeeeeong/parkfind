package seeeeeeong.parkfind.domain.park.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seeeeeeong.parkfind.domain.park.cache.ParkRedisTemplateService;
import seeeeeeong.parkfind.domain.park.dto.ParkDto;
import seeeeeeong.parkfind.domain.park.entity.Park;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkSearchService {

    private final ParkRepositoryService parkRepositoryService;
    private final ParkRedisTemplateService parkRedisTemplateService;

    public List<ParkDto> searchParkDtoList() {

        List<ParkDto> parkDtoList = parkRedisTemplateService.findAll();
        if(!parkDtoList.isEmpty()) return parkDtoList;

        return parkRepositoryService.findAll()
                .stream()
                .map(this::convertToParkDto)
                .collect(Collectors.toList());
    }

    private ParkDto convertToParkDto(Park park) {
        return ParkDto.builder()
                .id(park.getId())
                .parkAddress(park.getParkAddress())
                .parkName(park.getParkName())
                .latitude(park.getLatitude())
                .longitude(park.getLongitude())
                .build();
    }
}
