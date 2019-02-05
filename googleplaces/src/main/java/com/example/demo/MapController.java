package com.example.demo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MapController {
    private static final String api_key = "AIzaSyBV8WeWy332gfpOHzjcCrwVHDRzNsqqO48";
    private static final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private final RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/submit")
    @Cacheable("cache")
    public HttpEntity getHttpEntityResponse(@RequestParam String latitude,
                                            @RequestParam String longitude,
                                            @RequestParam String radius,
                                            @RequestParam(defaultValue = api_key) String key
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("location", latitude+","+longitude)
                .queryParam("radius", radius).queryParam("key", key);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                String.class);
        
        return response;
    }

}
