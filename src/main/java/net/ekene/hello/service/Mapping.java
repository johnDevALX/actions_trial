package net.ekene.hello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class Mapping {



    public static void trial() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        map.put("name", "AKin Ezekiel");
        map.put("meterNo", "2345678");
        map.put("discoCode", "Ib");

        System.out.println(map);
        System.out.println(map.get("name"));
        System.out.println(map.get("meterNo"));
        String s = objectMapper.writeValueAsString(map);
        MappedObj mappedObj = objectMapper.readValue(s, MappedObj.class);
        System.out.println(mappedObj);
        System.out.println(mappedObj.getName());
        System.out.println(mappedObj.getDiscoCode());
    }

    public static void main(String[] args) throws JsonProcessingException {
        trial();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MappedObj{
        private String name;
        private String meterNo;
        private String discoCode;
    }
}
