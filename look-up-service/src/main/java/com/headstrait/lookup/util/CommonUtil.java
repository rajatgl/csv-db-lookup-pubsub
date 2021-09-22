package com.headstrait.lookup.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

@Component
@Slf4j
public class CommonUtil {
    public static void delay(long delayMilliSeconds){
        try {
            sleep(delayMilliSeconds);
        }catch (Exception e){
            log.error("Exception is : "+e.getMessage());
        }
    }
}
