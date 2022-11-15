package com.hyunebin.dividend.Web.Persist.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Dividend {
    private LocalDateTime localDateTime;
    private String dividend;


}
