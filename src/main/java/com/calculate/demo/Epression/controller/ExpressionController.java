package com.calculate.demo.Epression.controller;


import com.calculate.demo.Epression.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExpressionController {
    @Autowired
    private ExpressionService expressionService;

    @PostMapping(value = "/api/getExpression")
    public ResponseEntity<?> getExpression(@RequestBody String date){
        String date1 = date.replace("=","");
        return ResponseEntity.ok(expressionService.getByDateTime(date1));
    }
    @PostMapping(value = "/api/exportExcel")
    public ResponseEntity<?> exportExcel(@RequestBody String date) throws IOException {
        String date1 = date.replace("=","");
        expressionService.exportExcel(date1);
        return ResponseEntity.ok("thanh cong roi moi nguoi oi");
    }
}
