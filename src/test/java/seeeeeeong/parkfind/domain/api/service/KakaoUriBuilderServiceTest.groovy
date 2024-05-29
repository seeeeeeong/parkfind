package seeeeeeong.parkfind.domain.api.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.nio.charset.StandardCharsets
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class KakaoUriBuilderServiceTest {

    @Autowired
    private KakaoUriBuilderService kakaoUriBuilderService;

    @Test
    void buildUriByAddressSearch_success() {
        // given
        String address = "서울 성북구"
        String charset = StandardCharsets.UTF_8;

        // when
        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);
        String decodedResult = URLDecoder.decode(uri.toString(), charset);

        // then
        assertEquals("https://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구", decodedResult);
    }

}
