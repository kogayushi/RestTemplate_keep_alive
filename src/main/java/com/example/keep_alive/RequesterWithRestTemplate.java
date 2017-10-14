package com.example.keep_alive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequesterWithRestTemplate {

    private final RestTemplate restTemplate ;

    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void request1() throws InterruptedException {
        log.info("start =========================================================================");
        int loop = 40;
        MyThread[] threads = new MyThread[loop];
        for (int i = 0; i < loop; i++) {
            threads[i] = new MyThread(restTemplate);
        }

        for (int i = 0; i < loop; i++) {
            threads[i].start();
        }

        for (int i = 0; i < loop; i++) {
            threads[i].join();
        }
        log.info("e n d =========================================================================");
    }

    @RequiredArgsConstructor
    static class MyThread extends Thread {


        private final RestTemplate restTemplate;

        @Override
        public void run() {
            StopWatch watch = new StopWatch();
            watch.start();
            log.info("will execute");
            ResponseEntity<String> response = restTemplate.getForEntity("http://192.168.33.11", String.class);
            log.info(response.getStatusCode().toString());
            watch.stop();
            log.info("has executed, {}", watch.getTotalTimeMillis());
        }

    }
}
