package seeeeeeong.parkfind.domain.park.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import seeeeeeong.parkfind.common.config.RedisTestContainerConfig;
import seeeeeeong.parkfind.domain.park.entity.Park;
import seeeeeeong.parkfind.domain.park.repository.ParkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ParkRepositoryServiceTest extends RedisTestContainerConfig {

    @Autowired
    private ParkRepositoryService parkRepositoryService;

    @MockBean
    private ParkRepository parkRepository;

    @BeforeEach
    void setup() {
        parkRepository.deleteAll();
    }

    @Test
    void updateAddress_success() {
        // given
        String inputAddress = "서울특별시 중구 동호로11나길 47";
        String modifiedAddress = "서울 광진구 구의동";
        String name = "충현어린이공원 공영주차장";

        Park park = Park.builder()
                .parkAddress(inputAddress)
                .parkName(name)
                .build();

        // when
        when(parkRepository.findById(park.getId())).thenReturn(Optional.of(park));

        // then
        assertDoesNotThrow(() -> parkRepositoryService.updateAddress(park.getId(), modifiedAddress));
    }




    @Test
    void findAll_success() {
        // given
        List<Park> parkList = new ArrayList<>();
        parkList.add(
                Park.of("성곽 공영주차장", "서울특별시 중구 동호로17길 201", 37.550508, 127.004839)
        );


        // when
        when(parkRepository.findAll()).thenReturn(parkList);

        // then
        assertEquals(1, parkList.size());
        for (int i = 0; i < parkList.size(); i++) {
            assertEquals("성곽 공영주차장", parkList.get(i).getParkName());
            assertEquals("서울특별시 중구 동호로17길 201", parkList.get(i).getParkAddress());
        }
    }
}