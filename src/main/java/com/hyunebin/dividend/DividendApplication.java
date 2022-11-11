package com.hyunebin.dividend;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DividendApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DividendApplication.class, args);

        Connection connection = Jsoup.connect("https://finance.yahoo.com/quote/COKE/history?period1=99100800&period2=1668038400&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true");
        try {
            Document document = connection.get();

            Elements elements = document.getElementsByAttributeValue("data-test","historical-prices");

            Element element = elements.get(0);

            Element tbody = element.children().get(1);//body = 1 head = 0 foot = 2

            for(Element e : tbody.children()){
                String txt = e.text();
                if(!txt.endsWith("Dividend")){
                    continue;
                }

                System.out.println(txt);
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
