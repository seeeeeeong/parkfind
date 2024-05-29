package seeeeeeong.parkfind.domain.park.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import seeeeeeong.parkfind.domain.api.dto.DocumentDto;
import seeeeeeong.parkfind.domain.api.dto.KakaoApiResponseDto;
import seeeeeeong.parkfind.domain.api.service.KakaoAddressSearchService;
import seeeeeeong.parkfind.domain.direction.dto.OutputDto;
import seeeeeeong.parkfind.domain.direction.entity.Direction;
import seeeeeeong.parkfind.domain.direction.service.Base62Service;
import seeeeeeong.parkfind.domain.direction.service.DirectionService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    @Value("${park.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendParkList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);
        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    private OutputDto convertToOutputDto(Direction direction) {

        return OutputDto.builder()
                .parkName(direction.getTargetParkName())
                .parkAddress(direction.getTargetAddress())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
