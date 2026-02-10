package com.sentinel.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// CRITICAL: This allows your React app (on port 5173) to talk to this Java app
@CrossOrigin(origins = "http://localhost:5173")
public class TestController {

  @GetMapping("/api/status")
  public String getStatus() {
    return "Connected: The SEB Banking Backend is online!";
  }
}