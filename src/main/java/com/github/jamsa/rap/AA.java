package com.github.jamsa.rap;

import java.util.Map;

import org.springframework.util.AntPathMatcher;

public class AA {


    public static void main(String[] args) {
        String format = "location/{state}/{city}";
        String actualUrl = "location/washington/seattle";
        AntPathMatcher pathMatcher = new AntPathMatcher();
        Map<String, String> variables = pathMatcher.extractUriTemplateVariables(format, actualUrl);
        System.out.println(variables.get("state"));
        System.out.println(variables.get("city"));

    }
}
