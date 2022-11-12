package com.hyunebin.dividend.Web.Service;

import com.hyunebin.dividend.Web.Persist.Model.Company;

public interface CompanyService {
    Company save(String string);
    Company storeCompanyAndDividend(String ticker);
}
