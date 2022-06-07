package com.jyanoos.qna;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class javaTest {
    @Test
    void temp(){
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        log.info("{}",a);

    }
}
