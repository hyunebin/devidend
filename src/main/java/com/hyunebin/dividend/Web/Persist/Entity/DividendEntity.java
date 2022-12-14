package com.hyunebin.dividend.Web.Persist.Entity;


import com.hyunebin.dividend.Web.Persist.Model.Dividend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Dividend")
@Getter
@ToString
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"companyId","date"}
                )
        }
)

public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;

    public DividendEntity(long companyId, Dividend dividend){
        this.companyId = companyId;
        this.date = dividend.getLocalDateTime();
        this.dividend = dividend.getDividend();
    }
}
