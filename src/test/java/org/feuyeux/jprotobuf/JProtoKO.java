package org.feuyeux.jprotobuf;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JProtoKO {
    @Test
    public void testBenchmark() throws Exception {
        Options options =
                new OptionsBuilder()
                        .include("org.feuyeux.jprotobuf.*Bench")
                        .forks(1)
                        .threads(1)
                        .warmupIterations(1)
                        .measurementIterations(1)
                        .mode(Mode.Throughput)
                        .result("benchmark.json")
                        .resultFormat(ResultFormatType.JSON)
                        .build();
        new Runner(options).run();
    }
}
