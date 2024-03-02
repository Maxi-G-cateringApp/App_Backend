package com.catering_app.Catering_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

    @GetMapping("/admin/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("demo admin");

    }

    @GetMapping("/demo")
    public ResponseEntity<String> demo2(){
        return ResponseEntity.ok("demo user");

    }
}
