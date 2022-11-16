package com.hyunebin.dividend.Web.Service;

import com.hyunebin.dividend.Web.Persist.Entity.CompanyEntity;
import com.hyunebin.dividend.Web.Persist.Entity.DividendEntity;
import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Persist.Model.Dividend;
import com.hyunebin.dividend.Web.Persist.Model.ScrapedResult;
import com.hyunebin.dividend.Web.Repository.Company.CompanyRepository;
import com.hyunebin.dividend.Web.Repository.Dividend.DividendRepository;
import com.hyunebin.dividend.Web.Scraper.Scarper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class CompanyServiceImp implements CompanyService {

    private final Scarper yahooFinanceScarper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Trie trie;

    @Override
    public void addAutoComplete(String keyword) {
        trie.put(keyword, null);
    }

    @Override
    public List<String> autoComplete(String keyword) {
        return (List<String>) trie.prefixMap(keyword).keySet().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAutoCompleteKeyword(String keyword) {
        trie.remove(keyword);
    }

    @Override
    public Company save(String ticker) {
        boolean exist = companyRepository.existsByTicker(ticker);
        if(exist){
            throw new RuntimeException("회사가 이미 존재합니다.");
        }

        return storeCompanyAndDividend(ticker);
    }

    @Override
    public Company storeCompanyAndDividend(String ticker) {
        //ticker를 통해 회사 정보 스크랩핑

        Optional<Company> companyOptional = Optional.ofNullable(yahooFinanceScarper.scrapCompanyByTicker(ticker));
        //회사 정보가 존재x
        if (companyOptional.isEmpty()) {
            throw new RuntimeException("ticker에대한 회사 정보가 존재하지 않습니다.");
        }

        Company company = companyOptional.get();

        ScrapedResult scrapedResult = yahooFinanceScarper.scrap(company);

        //결과
        CompanyEntity companyEntity = companyRepository.save(new CompanyEntity(company));

        List<DividendEntity> dividendEntities = scrapedResult.getDividendList().stream().map(e -> new DividendEntity(companyEntity.getId(), e)).collect(Collectors.toList());
        System.out.println(dividendEntities);
        dividendRepository.saveAll(dividendEntities);

        return company;
    }

    @Override
    public String deleteCompany(String ticker) {
        var result = companyRepository.findByTicker(ticker).orElseThrow(()->new RuntimeException("존재하지 않는 회사입니다."));

        dividendRepository.deleteAllByCompanyId(result.getId());
        companyRepository.delete(result);

        deleteAutoCompleteKeyword(result.getName());

        return result.getName();
    }

    @Override
    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }
}
