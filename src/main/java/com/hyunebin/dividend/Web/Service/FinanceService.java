package com.hyunebin.dividend.Web.Service;

import com.hyunebin.dividend.Web.Persist.Model.ScrapedResult;

public interface FinanceService {
    public ScrapedResult getDividendByCompany(String company);
}
