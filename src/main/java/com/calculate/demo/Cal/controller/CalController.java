package com.calculate.demo.Cal.controller;


import com.calculate.demo.Cal.service.CalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalController {
    @Autowired
    private CalService calService;
    @PostMapping(value = "/createData")
    public ResponseEntity<?> createProduct(@RequestBody CalService.CalInput calInput){
            calService.createData(calInput);
            return ResponseEntity.ok("create product successfully");
    }
    @PostMapping(value = "/api/calculate")
    public ResponseEntity<?> calculate(@RequestBody String expression){

        return ResponseEntity.ok(calService.calData(expression));

    }
    @GetMapping(value = "/getValue")
    public ResponseEntity<?> getValue(@RequestBody String keyA){
        calService.getAllValue(keyA);
        return ResponseEntity.ok(calService.getAllValue(keyA));
    }

}
