package seeeeeeong.parkfind.domain.direction.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class Base62ServiceTest {

    @Autowired
    private Base62Service base62Service;

    @Test
    void checkBase62EncoderDecoder() {
        // given
        long num = 5;

        // when
        String encodedId = base62Service.encodeDirectionId(num);
        long decodedId = base62Service.decodeDirectionId(encodedId);

        // then
        assertEquals(num, decodedId);
    }
}