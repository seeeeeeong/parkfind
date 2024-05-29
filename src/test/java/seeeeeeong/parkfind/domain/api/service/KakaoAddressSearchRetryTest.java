package seeeeeeong.parkfind.domain.api.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import seeeeeeong.parkfind.common.config.RedisTestContainerConfig;
import seeeeeeong.parkfind.domain.api.dto.DocumentDto;
import seeeeeeong.parkfind.domain.api.dto.KakaoApiResponseDto;
import seeeeeeong.parkfind.domain.api.dto.MetaDto;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@SpringBootTest
public class KakaoAddressSearchRetryTest extends RedisTestContainerConfig {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @MockBean
    private KakaoUriBuilderService kakaoUriBuilderService;

    private MockWebServer mockWebServer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String inputAddress = "서울 성북구 종암로 10길";

    @BeforeEach
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        System.out.println(mockWebServer.getPort());
        System.out.println(mockWebServer.url("/"));
    }

    @AfterEach
    public void cleanup() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void requestAddressSearchRetrySuccess() throws Exception {
        MetaDto metaDto = new MetaDto(1);
        DocumentDto documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build();
        KakaoApiResponseDto expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto));
        URI uri = mockWebServer.url("/").uri();

        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(expectedResponse)));

        Mockito.when(kakaoUriBuilderService.buildUriByAddressSearch(anyString())).thenReturn(uri);

        KakaoApiResponseDto kakaoApiResult = kakaoAddressSearchService.requestAddressSearch(inputAddress);

        Mockito.verify(kakaoUriBuilderService, times(2)).buildUriByAddressSearch(inputAddress);
        assertEquals(1, kakaoApiResult.getDocumentList().size());
        assertEquals(1, kakaoApiResult.getMetaDto().getTotalCount());
        assertEquals(inputAddress, kakaoApiResult.getDocumentList().get(0).getAddressName());
    }

    @Test
    public void requestAddressSearchRetryFail() throws Exception {
        URI uri = mockWebServer.url("/").uri();

        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));

        Mockito.when(kakaoUriBuilderService.buildUriByAddressSearch(anyString())).thenReturn(uri);

        KakaoApiResponseDto result = kakaoAddressSearchService.requestAddressSearch(inputAddress);

        Mockito.verify(kakaoUriBuilderService, times(2)).buildUriByAddressSearch(inputAddress);
        assertNull(result);
    }
}

