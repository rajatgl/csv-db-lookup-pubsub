package com.headstrait.datasource.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

@Slf4j
public class CommonUtils {
    private static final StopWatch stopWatch = new StopWatch();

    /**
     * commence stop watch timer
     */
    public static void startTimer(){
        stopWatchReset();
        stopWatch.start();
        log.info("timer started.");
    }

    /**
     * record time taken
     */
    public static void timeTaken(){
        stopWatch.stop();
        log.info("Timer stopped, total Time Taken : " +stopWatch.getTime());
    }

    /**
     * stop watch reset
     */
    public static void stopWatchReset(){
        stopWatch.reset();
        log.info("Stop watch reset");
    }
}
