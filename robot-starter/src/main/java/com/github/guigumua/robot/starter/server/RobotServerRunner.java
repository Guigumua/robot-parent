package com.github.guigumua.robot.starter.server;

import com.github.guigumua.robot.starter.server.RobotServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RobotServerRunner implements ApplicationRunner {
    private final RobotServer server;
    private final ApplicationContext context;

    public RobotServerRunner(RobotServer server, ApplicationContext context) {
        this.server = server;
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        server.setApplicationContext(context);
        server.start();
    }
}
