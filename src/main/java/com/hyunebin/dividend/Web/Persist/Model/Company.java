package com.hyunebin.dividend.Web.Persist.Model;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Company {
    private String ticker;
    private String name;


}
