package seeeeeeong.parkfind.domain.park.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seeeeeeong.parkfind.common.config.RedisTestContainerConfig;
import seeeeeeong.parkfind.domain.park.entity.Park;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ParkRepositoryTest extends RedisTestContainerConfig {

    @Autowired
    private ParkRepository parkRepository;

    @BeforeEach
    void setup() {
        parkRepository.deleteAll();
    }

    @Test
    void save() {
        // given
        String address = "서울특별시 중구 동호로11나길 47";
        String name = "충현어린이공원 공영주차장";
        double latitude = 37.552118;
        double longitude = 127.0057006;

        Park park = Park.builder()
                .parkAddress(address)
                .parkName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        // when
        Park result = parkRepository.save(park);

        // then
        assertThat(result.getParkAddress()).isEqualTo(address);
        assertThat(result.getParkName()).isEqualTo(name);
        assertThat(result.getLatitude()).isEqualTo(latitude);
        assertThat(result.getLongitude()).isEqualTo(longitude);
    }

    @Test
    void saveAll() {
        // given
        String address = "서울특별시 중구 동호로11나길 47";
        String name = "충현어린이공원 공영주차장";
        double latitude = 37.552118;
        double longitude = 127.0057006;

        Park park = Park.builder()
                .parkAddress(address)
                .parkName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        // when
        parkRepository.saveAll(Arrays.asList(park));
        List<Park> result = parkRepository.findAll();

        // then
        assertThat(result).hasSize(1);
    }

}