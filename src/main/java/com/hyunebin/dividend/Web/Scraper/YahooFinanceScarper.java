package com.hyunebin.dividend.Web.Scraper;

import com.hyunebin.dividend.Web.Persist.Model.Company;
import com.hyunebin.dividend.Web.Persist.Model.Dividend;
import com.hyunebin.dividend.Web.Persist.Model.ScrapedResult;
import com.hyunebin.dividend.Web.Persist.Model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YahooFinanceScarper implements Scarper {
    private static final String URL ="https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String TICKER_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400;


    public ScrapedResult scrap(Company company){
        //var = 모든 변수를 칭할 수 있다.
        var scrapedResult  = new ScrapedResult();

        scrapedResult.setCompany(company);

        try {
            long endTime = System.currentTimeMillis() / 1000 ;

            String url = String.format(URL, company.getTicker(),START_TIME,endTime);
            Connection connection = Jsoup.connect(url);
            //HTML 문서를 불러오게됨
            Document document = connection.get();

            //Parsing
            Elements parsingElement = document.getElementsByAttributeValue("data-test","historical-prices");
            Element tableElement = parsingElement.get(0);
            Element tbody = tableElement.children().get(1);//body = 1 head = 0 foot = 2

            //받은 배당금에 대한 정보를 저장
            List<Dividend> dividendList = new ArrayList<>();
            for(Element e : tbody.children()){
                String txt = e.text();
                if(!txt.endsWith("Dividend")){
                    continue;
                }

                String[] splits = txt.split(" ");
                int month = Month.strToNum(splits[0]);
                if(month == -1){
                    throw new RuntimeException("Month 값이 올바르지 않습니다." + splits[0]);
                }

                int day = Integer.parseInt(splits[1].replace(",",""));
                int year = Integer.parseInt(splits[2]);


                String dividend = splits[3];


                //Builder pattern
                dividendList.add( Dividend.builder()
                        .localDateTime(LocalDateTime.of(year,month,day,0,0))
                        .dividend(dividend).build());
            }

            scrapedResult.setDividendList(dividendList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return scrapedResult;
    }

    public Company scrapCompanyByTicker(String ticker){

        String url = String.format(TICKER_URL, ticker, ticker);
        try {
            Document document =  Jsoup.connect(url).get();
            Element titleElement = document.getElementsByTag("h1").get(0);
            String title = titleElement.text().split(" ")[0].trim();


            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
