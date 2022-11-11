package com.hyunebin.dividend.Web.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @GetMapping("/company/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword){
        return null;
    }

    @GetMapping()
    public ResponseEntity<?> searchCompany(){
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> addCompany(){
        return null;
    }

    @DeleteMapping()
    public ResponseEntity<?> delCompany(){
        return null;
    }
}
