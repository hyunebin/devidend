package com.hyunebin.dividend.Web.Repository.Company;


import com.hyunebin.dividend.Web.Persist.Entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
     boolean existsByTicker(String ticker);

     Optional<CompanyEntity> findByName(String company);
}
