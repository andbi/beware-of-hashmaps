package net.bichkevski.java.hashmap;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

class MainTest {

    @Test
    void benchMark() throws RunnerException {
        TimeValue duration = TimeValue.milliseconds(500L);
        int iterations = 10;
        Options opt = new OptionsBuilder()
                .include(MapBenchmark.class.getSimpleName())
                .warmupIterations(iterations)
                .warmupTime(duration)
                .measurementIterations(iterations)
                .measurementTime(duration)
                .build();

        new Runner(opt).run();
    }

}