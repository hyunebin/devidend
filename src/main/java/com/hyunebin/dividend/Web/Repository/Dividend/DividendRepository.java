package com.hyunebin.dividend.Web.Repository.Dividend;


import com.hyunebin.dividend.Web.Persist.Entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

    List<DividendEntity> findAllByCompanyId(Long CompanyId);
}
