package com.ms.back.global.component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupTimer {

    private final Timer timer;

    public StartupTimer(MeterRegistry registry) {
        this.timer = Timer.builder("application.startup.time")
                .description("App startup time")
                .register(registry);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        timer.record(() -> {
            // 앱 시작 시점 기록
            System.out.println("Application is ready.");
        });
    }
}