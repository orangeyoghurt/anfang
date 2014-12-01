package com.sanss.octupus.prototype.anfang;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Command line starter class, which initializing spring.
 * Created by simon on 2014/12/1.
 */
public class Main {

    public static void main(String[] args) {
        String[] paths = new String[] {"classpath:applicationContext.xml"};

        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);

        Main self = ctx.getBean(Main.class);

        if (self.init()) {
            try {
                self.execute();
            } catch(Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void execute() {
        // TODO start daemon thread there.
    }

    private boolean init() {
        return true;
    }
}
