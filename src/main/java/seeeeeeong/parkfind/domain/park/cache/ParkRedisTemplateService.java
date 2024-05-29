package seeeeeeong.parkfind.domain.park.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import seeeeeeong.parkfind.domain.park.dto.ParkDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParkRedisTemplateService {

    private static String CACHE_KEY = "PARK";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(ParkDto parkDto) {
        if(Objects.isNull(parkDto) || Objects.isNull(parkDto.getId())) {
            return;
        }
        try {
            hashOperations.put(CACHE_KEY,
                    parkDto.getId().toString(),
                    serializeParkDto(parkDto));
        } catch (Exception e) {

        }
    }

    public List<ParkDto> findAll() {

        try {
            List<ParkDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                ParkDto parkDto = deserializeParkDto(value);
            }
            return list;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
    }


    private String serializeParkDto(ParkDto parkDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(parkDto);
    }

    private ParkDto deserializeParkDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, ParkDto.class);
    }

}
