package com.hyunebin.dividend;

import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Scraper.YahooFinanceScarper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DividendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendApplication.class, args);

    }
}
