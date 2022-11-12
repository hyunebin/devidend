package com.hyunebin.dividend.Web.Persist.Model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Dividend {
    private LocalDateTime localDateTime;
    private String dividend;
}
