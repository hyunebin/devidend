package com.hyunebin.dividend.Web.Persist.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

public class ScrapedResult {
    private Company company;
    private List<Dividend> dividendList;

    public ScrapedResult(){
        this.dividendList = new ArrayList<>();
    }
}
