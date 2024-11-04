package org.example.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//    @PostMapping(consumes = "application/json", produces = "application/json")
//    public ResponseEntity<String> testEndpoint(@RequestBody Map<String, Object> payload) {
//        return ResponseEntity.ok("Success");
//    }
//}


@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> testEndpoint(
            @RequestBody Map<String, Object> payload,
            @RequestParam("param1") String param1,
            @RequestParam("param2") String param2) {

//        String param11 = (String) payload.get("param1");
//        String param21 = (String) payload.get("param2");
        String key = (String) payload.get("key");

        String res = param1 + param2 + key;

        return ResponseEntity.ok(res);
    }
}