package seeeeeeong.parkfind.domain.direction.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import seeeeeeong.parkfind.domain.api.dto.DocumentDto;
import seeeeeeong.parkfind.domain.api.service.KakaoCategorySearchService;
import seeeeeeong.parkfind.domain.direction.entity.Direction;
import seeeeeeong.parkfind.domain.direction.repository.DirectionRepository;
import seeeeeeong.parkfind.domain.park.dto.ParkDto;
import seeeeeeong.parkfind.domain.park.service.ParkSearchService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DirectionServiceTest {

    @Autowired
    private DirectionService directionService;

    @MockBean
    private ParkSearchService parkSearchService;

    @MockBean
    private DirectionRepository directionRepository;

    @MockBean
    private KakaoCategorySearchService kakaoCategorySearchService;

    @MockBean
    private Base62Service base62Service;

    private List<ParkDto> parkList;

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;

    @BeforeEach
    void setup() {
        parkList = new ArrayList<>();
        parkList.add(
                ParkDto.builder()
                        .id(1L)
                        .parkName("성곽 공영주차장")
                        .parkAddress("서울특별시 중구 동호로17길 201")
                        .latitude(37.550508)
                        .longitude(127.004839)
                        .build());
        parkList.add(
                ParkDto.builder()
                        .id(2L)
                        .parkName("약수동 공영주차장")
                        .parkAddress("서울특별시 중구 동호로8다길 22")
                        .latitude(37.552237)
                        .longitude(127.0133313)
                        .build());
    }

    @Test
    void buildDirectionList_success() {
        // given
        String address = "서울 중구 동호로8다길 22";
        double inputLatitude = 37.552237;
        double inputLongitude = 127.0133313;

        DocumentDto documentDto = DocumentDto.builder()
                .addressName(address)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build();

        // when
        List<Direction> results = parkList.stream().map(park ->
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

        // then
        assertEquals(2, results.size());
        assertEquals("약수동 공영주차장", results.get(0).getTargetParkName());
        assertEquals("성곽 공영주차장", results.get(1).getTargetParkName());
    }


    @Test
    void buildDirectionList_success_10km() {
        // given
        parkList.add(
                ParkDto.builder()
                        .id(3L)
                        .parkName("삼성 공영주차장")
                        .parkAddress("서울특별시 금천구 독산로24마길 30")
                        .latitude(37.45458796)
                        .longitude(126.9090376)
                        .build());

        String address = "서울 중구 동호로8다길 22";
        double inputLatitude = 37.552237;
        double inputLongitude = 127.0133313;

        DocumentDto documentDto = DocumentDto.builder()
                .addressName(address)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build();

        // when
        List<Direction> results = parkList.stream().map(park ->
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

        // then
        assertEquals(2, results.size());
        assertEquals("약수동 공영주차장", results.get(0).getTargetParkName());
        assertEquals("성곽 공영주차장", results.get(1).getTargetParkName());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

}