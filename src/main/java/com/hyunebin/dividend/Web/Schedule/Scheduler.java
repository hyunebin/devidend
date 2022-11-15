package com.hyunebin.dividend.Web.Schedule;

import com.hyunebin.dividend.Web.Persist.CacheKey;
import com.hyunebin.dividend.Web.Persist.Entity.CompanyEntity;
import com.hyunebin.dividend.Web.Persist.Entity.DividendEntity;
import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Persist.Model.ScrapedResult;
import com.hyunebin.dividend.Web.Repository.Company.CompanyRepository;
import com.hyunebin.dividend.Web.Repository.Dividend.DividendRepository;
import com.hyunebin.dividend.Web.Scraper.YahooFinanceScarper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Component
@Cacheable
@EnableScheduling
public class Scheduler {
    private final CompanyRepository companyRepository;
    private final YahooFinanceScarper yahooFinanceScarper;

    private final DividendRepository dividendRepository;
    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling()  {
        log.info("스크래핑 시작");
        // 조회
        List<CompanyEntity> companyEntityList = companyRepository.findAll();
        // 스크래핑
        for(var company : companyEntityList){
            ScrapedResult scrapedResult = yahooFinanceScarper.scrap(new Company(company.getTicker(), company.getName()));

            scrapedResult.getDividendList().stream().map(e -> new DividendEntity(company.getId(), e)).forEach(e->{
                boolean exist = dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                if(!exist){
                    dividendRepository.save(e);
                }
            }
            );

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        //비교후 중복제거 후 새로운 값 update

    }
}
