package net.bichkevski.java.hashmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, warmups = 1)
public class MapBenchmark {
    private static final int OBJECT_COUNT = 10_000;

    @Benchmark
    public void scan(BenchmarkState state, Blackhole bh) {
        int i = 0;
        for (Object o : state.hashMap.keySet()) {
            i ^= o.hashCode();
        }
        bh.consume(i);
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        Map<Object, Object> hashMap = new HashMap<>();

        @Param({"1", "5", "10", "100", "1000"})
        int stress;

        @Setup(Level.Trial)
        public void init() {

            // 1. Fill the map with a large data set, ...
            fillMap(this.hashMap, stress * OBJECT_COUNT);
            // 2. clear it, ....
            this.hashMap.clear();
            // 3. fill it with the default number of objects, ...
            fillMap(this.hashMap, OBJECT_COUNT);
            // 4. test the scan time....
        }

        static void fillMap(Map<Object, Object> map, int objectCount) {
            for (int i = 0; i < objectCount; i++) {
                map.put(new Object(), new Object());
            }
        }
    }
}