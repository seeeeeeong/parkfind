package seeeeeeong.parkfind.domain.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seeeeeeong.parkfind.domain.api.dto.KakaoApiResponseDto;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;
    private final RestTemplate restTemplate;
    private static final String PARK_CATEGORY = "PK6"; // 약국 카테고리

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public KakaoApiResponseDto requestParkCategorySearch(double latitude, double longitude, double radius) {

        URI uri = kakaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, PARK_CATEGORY);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
    }
}
