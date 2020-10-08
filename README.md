# Java HashMap sustainability test

**Instances of `java.util.HashMap` never shrink** - some developers know that, some still don't. 

"_What's the big deal_", you may ask. The answer is deteriorating scan performance when 
used as a dynamic data storage. 
Generally speaking, the iteration complexity of a `HashMap` instance **is not O(N)**,
it depends on its history.

This project presents a simple `HashMap` sustainability benchmark with the following scenario:
1. Stress a map with `K * N` records
2. Clear it.
3. Fill the same map with `N` records
4. Measure the scan time

, where `N=10_000` is a constant, and the stress parameter `K` takes values `[1,5,10,100,1000]`

 ### Running the test
 1. Checkout the project
 2. run `mvn clean test`

 ### Test results
 
    Benchmark          (stress)  Mode  Cnt   Score   Error  Units
    MapBenchmark.scan         1  avgt   10   0.183 ± 0.001  ms/op
    MapBenchmark.scan         5  avgt   10   0.266 ± 0.023  ms/op
    MapBenchmark.scan        10  avgt   10   0.400 ± 0.044  ms/op
    MapBenchmark.scan       100  avgt   10   2.358 ± 0.083  ms/op
    MapBenchmark.scan      1000  avgt   10  21.636 ± 2.113  ms/op


Hardware: `macOS 10.15.6, Java HotSpot(TM) 64-Bit Server VM (build 15+36-1562)`

### Summary
Be careful iterating Java's stock hash collections when using them as a long-living, 
dynamic, and high-load data storage. The iteration time over a HashMap depends 
on its history, and it never goes down.

**Performance-wise, iterating over a single entry map, that previously contained a billion entries, is 
like iterating over a billion entry map.** 

Developers should read the official Javadoc on the `java.util.HashMap` as: 
>"... Iteration over
>collection views requires time proportional to **the historical maximum** of the 
>"capacity" of the {@code HashMap} instance (the number of buckets) plus its size (the number
>of key-value mappings)."

