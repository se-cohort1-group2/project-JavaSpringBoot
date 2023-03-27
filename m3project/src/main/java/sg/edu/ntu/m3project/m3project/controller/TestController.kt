package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
class TestController {
    
    @GetMapping("/food")
    fun index(): String {
        return "Test"
    }

}