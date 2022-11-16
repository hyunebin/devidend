package com.hyunebin.dividend.Web.Controller;

import com.hyunebin.dividend.Web.Persist.CacheKey;
import com.hyunebin.dividend.Web.Persist.Entity.CompanyEntity;
import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor

public class CompanyController {

    private final CompanyService companyService;
    private final CacheManager redisCacheManager;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword){
        var result = companyService.autoComplete(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    @PreAuthorize("hasRole('READ')")
    public ResponseEntity<?> searchCompany(final Pageable pageable){

        Page<CompanyEntity> companyEntityList = companyService.getAllCompany(pageable);

        return ResponseEntity.ok(companyEntityList);

    }


    /**
     * 회사 및 배당금 정보 추가
     * **/
    @PostMapping()
    @PreAuthorize("hasRole('WRITE')")
    public ResponseEntity<?> addCompany(@RequestBody Company request){
        String ticker = request.getTicker();
        if(ObjectUtils.isEmpty(ticker)){
            throw new RuntimeException("ticker 값이 존재하지 않습니다.");
        }



        Company company = companyService.save(ticker);
        companyService.addAutoComplete(company.getName());
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{ticker}")
    @PreAuthorize("hasRole('WRITE')")
    public ResponseEntity<?> delCompany(String ticker){
        String companyName = companyService.deleteCompany(ticker);
        clearFinanceCache(companyName);

        return ResponseEntity.ok(companyName);
    }

    public void clearFinanceCache(String companyname){
            redisCacheManager.getCache(CacheKey.KEY_FINANCE).evict(companyname);
    }
}
