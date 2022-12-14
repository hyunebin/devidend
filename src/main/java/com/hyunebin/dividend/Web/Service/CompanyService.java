package com.hyunebin.dividend.Web.Service;

import com.hyunebin.dividend.Web.Persist.Entity.CompanyEntity;
import com.hyunebin.dividend.Web.Persist.Model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    Company save(String string);
    Company storeCompanyAndDividend(String ticker);

    Page<CompanyEntity> getAllCompany(final Pageable pageable);

    public void addAutoComplete(String keyword);

    public List<String> autoComplete(String keyword);

    public void deleteAutoCompleteKeyword(String keyword);

    public String deleteCompany(String ticker);
}
