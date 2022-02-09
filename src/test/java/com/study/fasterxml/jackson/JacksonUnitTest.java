package com.study.fasterxml.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SpringExtension.class)
public class JacksonUnitTest {
    private ObjectMapper mapper;
    private Map<String, String> map;
    private MP3Player mp3Player;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        map = new HashMap<>();

        mapper.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
    }

    @Test
    public void getConvertedObject() {
        // @JsonInclude(JsonInclude.Include.NON_NULL) 외 기본 옵션 사용 시 테스트
        map.put("volume", "10");
        map.put("modelNumber", "MEUN-02-2022");

        mp3Player = mapper.convertValue(map, MP3Player.class);

        assertThat(mp3Player.getVolume(), is(10));
        assertThat(mp3Player.getModelNumber(), is("MEUN-02-2022"));
    }

    @Test
    public void getConvertedEmptyObject() {
        // @JsonSetter(nulls = Nulls.SKIP) 사용 시 테스트
        map.put("volume", "");
        map.put("modelNumber", "");

        mp3Player = mapper.convertValue(map, MP3Player.class);

        assertThat(mp3Player.getVolume(), is(0));
        assertThat(mp3Player.getModelNumber(), is(""));
    }
}
