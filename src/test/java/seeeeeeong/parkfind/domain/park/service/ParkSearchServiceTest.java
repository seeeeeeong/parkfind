package seeeeeeong.parkfind.domain.park.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import seeeeeeong.parkfind.common.config.RedisTestContainerConfig;
import seeeeeeong.parkfind.domain.direction.entity.Direction;
import seeeeeeong.parkfind.domain.park.cache.ParkRedisTemplateService;
import seeeeeeong.parkfind.domain.park.dto.ParkDto;
import seeeeeeong.parkfind.domain.park.entity.Park;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ParkSearchServiceTest extends RedisTestContainerConfig {

    @Autowired
    private ParkSearchService parkSearchService;

    @MockBean
    private ParkRepositoryService parkRepositoryService;

    @MockBean
    private ParkRedisTemplateService parkRedisTemplateService;

    private List<Park> parkList;

    @BeforeEach
    void setup() {
        parkList = new ArrayList<>();
        parkList.add(
                Park.builder()
                        .parkName("성곽 공영주차장")
                        .parkAddress("서울특별시 중구 동호로17길 201")
                        .latitude(37.550508)
                        .longitude(127.004839)
                        .build());
        parkList.add(
                Park.builder()
                        .parkName("약수동 공영주차장")
                        .parkAddress("서울특별시 중구 동호로8다길 22")
                        .latitude(37.552237)
                        .longitude(127.0133313)
                        .build());
    }

    @Test
    void searchParkDtoList_redis() {
        // when
        List<ParkDto> parkDtoList = new ArrayList<>();
        when(parkRedisTemplateService.findAll()).thenReturn(parkDtoList);

        // when
        List<ParkDto> actualParkDtoList = parkSearchService.searchParkDtoList();

        // then
        assertEquals(parkDtoList, actualParkDtoList);
    }

    @Test
    void searchParkDtoList() {
        // given
        when(parkRedisTemplateService.findAll()).thenReturn(new ArrayList<>());
        when(parkRepositoryService.findAll()).thenReturn(parkList);

        // when
        List<ParkDto> actualParkDtoList = parkSearchService.searchParkDtoList();

        // then
        assertEquals(parkList.size(), actualParkDtoList.size());

    }

}