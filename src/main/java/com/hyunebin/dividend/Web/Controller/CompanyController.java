package com.hyunebin.dividend.Web.Controller;

import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword){
        return null;
    }

    @GetMapping()
    public ResponseEntity<?> searchCompany(){
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> addCompany(@RequestBody Company request){
        String ticker = request.getTicker();
        if(ObjectUtils.isEmpty(ticker)){
            throw new RuntimeException("ticker 값이 존재하지 않습니다.");
        }

        Company company = companyService.save(ticker);


        return ResponseEntity.ok(company);
    }

    @DeleteMapping()
    public ResponseEntity<?> delCompany(){
        return null;
    }
}
