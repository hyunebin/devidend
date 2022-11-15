package com.hyunebin.dividend.Web.config;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Trie<String,String> tire(){
        return new PatriciaTrie<>();
    }
}
