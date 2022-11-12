package com.hyunebin.dividend.Web.Repository.Company;


import com.hyunebin.dividend.Web.Persist.Entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
     boolean existsByTicker(String ticker);
}
