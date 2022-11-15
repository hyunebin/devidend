package com.hyunebin.dividend.Web.Service;

import com.hyunebin.dividend.Web.Persist.Entity.CompanyEntity;
import com.hyunebin.dividend.Web.Persist.Entity.DividendEntity;
import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Persist.Model.Dividend;
import com.hyunebin.dividend.Web.Persist.Model.ScrapedResult;
import com.hyunebin.dividend.Web.Repository.Company.CompanyRepository;
import com.hyunebin.dividend.Web.Repository.Dividend.DividendRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceServiceImp implements FinanceService{

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    @Override
    @Cacheable(key = "#company", value = "finance") // redis 서버의 key-value와는 다름
    public ScrapedResult getDividendByCompany(String company) {
     //회사명으로 회사 조회
     CompanyEntity companyEntityOptional =companyRepository.findByName(company).orElseThrow(()-> new RuntimeException("존재하지 않는 회사입니다."));

     //배당금 정보를 조회 위에서 옵셔널 처리를 했기 떄문에 따로 예외 처리 x
     List<DividendEntity> dividendEntityList = dividendRepository.findAllByCompanyId(companyEntityOptional.getId());


     List<Dividend> dividendList = dividendEntityList.stream().map(e-> new Dividend(e.getDate(), e.getDividend())).collect(Collectors.toList());


        return new ScrapedResult(new Company(companyEntityOptional.getTicker(), companyEntityOptional.getName()), dividendList);
    }
}
