package seeeeeeong.parkfind.domain.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seeeeeeong.parkfind.common.config.RedisTestContainerConfig;
import seeeeeeong.parkfind.domain.api.dto.KakaoApiResponseDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KakaoAddressSearchServiceTest extends RedisTestContainerConfig {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @Test
    void requestAddressSearch_address_null() {
        // given
        String address = null;

        // when
        KakaoApiResponseDto result = kakaoAddressSearchService.requestAddressSearch(address);

        // then
        assertEquals(null, result);
    }

//    @Test
//    void requestAddressSearch_valid_address() {
//        // given
//        String address = "서울 성북구 종암로 10길";
//
//        // when
//        KakaoApiResponseDto result = kakaoAddressSearchService.requestAddressSearch(address);
//
//        // then
//        assertNotNull(result);
//        assertTrue(result.getDocumentList().size() > 0);
//        assertTrue(result.getMetaDto().getTotalCount() > 0);
//        assertNotNull(result.getDocumentList().get(0).getAddressName());
//    }


}