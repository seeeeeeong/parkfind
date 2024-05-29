package seeeeeeong.parkfind.domain.direction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;
import seeeeeeong.parkfind.domain.api.dto.DocumentDto;
import seeeeeeong.parkfind.domain.api.service.KakaoCategorySearchService;
import seeeeeeong.parkfind.domain.direction.entity.Direction;
import seeeeeeong.parkfind.domain.direction.repository.DirectionRepository;
import seeeeeeong.parkfind.domain.park.service.ParkSearchService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private final ParkSearchService parkSearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final Base62Service base62Service;


    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    public String findDirectionUrlById(String encodedId) {
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        Direction direction = directionRepository.findById(decodedId).orElse(null);

        String params = String.join(",", direction.getTargetParkName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params)
                .toUriString();

        return result;
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return parkSearchService.searchParkDtoList()
                .stream().map(park ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetParkName(park.getParkName())
                                .targetLatitude(park.getLatitude())
                                .targetLongitude(park.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                park.getLatitude(), park.getLongitude())
                                )
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());

    }

    public List<Direction> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto) {
        if(Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestParkCategorySearch(inputDocumentDto.getLatitude(), inputDocumentDto.getLongitude(), RADIUS_KM)
                .getDocumentList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetParkName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001) // km 단위
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

}
