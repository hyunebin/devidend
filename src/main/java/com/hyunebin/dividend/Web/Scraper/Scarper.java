package com.hyunebin.dividend.Web.Scraper;

import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Persist.Model.ScrapedResult;

public interface Scarper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
