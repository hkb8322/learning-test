package com.fasterxml.jackson.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SpringExtension.class)
public class JacksonUnitTest {

    @Test
    public void getConvertedObject() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        MP3Player mp3Player;

        map.put("volume", "10");
        map.put("modelNumber", "MEUN-02-2022");

        mapper.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

        mp3Player = mapper.convertValue(map, MP3Player.class);

        assertThat(mp3Player.getVolume(), is(10));
        assertThat(mp3Player.getModelNumber(), is("MEUN-02-2022"));
    }
}
