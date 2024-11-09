package org.feuyeux.jprotobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.jprotobuf.pojo.Person;
import org.feuyeux.jprotobuf.pojo.Persons;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@State(Scope.Thread)
@Slf4j
public class MoyenBench {
  private Persons persons;
  private ObjectMapper mapper;
  private Gson gson;
  private Codec<Persons> codec;

  @Setup
  public void setup() {
    persons = new Persons();
    List<Person> list = new ArrayList<>();
    IntStream.range(0, 20)
        .forEach(
            i -> {
              Person person = new Person();
              person.setId(Long.valueOf(i));
              person.setName("TOM");
              person.setAge(29);
              list.add(person);
            });
    persons.setPersons(list);

    mapper = new ObjectMapper();
    gson = new Gson();
    codec = ProtobufProxy.create(Persons.class);
  }

  @TearDown
  public void tearDown() {
    persons = null;
    mapper = null;
    gson = null;
    codec = null;
  }

  @Benchmark
  public void testJProtobuf() throws IOException {
    byte[] bytes = codec.encode(persons);
    Persons persons2 = codec.decode(bytes);
    log.debug("JProtobuf Persons:{}", persons2);
  }

  @Benchmark
  public void testFastJson() {
    String json = com.alibaba.fastjson.JSON.toJSONString(persons);
    Persons persons2 = com.alibaba.fastjson.JSON.parseObject(json, Persons.class);
    log.debug("FastJson Persons:{}", persons2);
  }

  @Benchmark
  public void testFast2Json() {
    String json = com.alibaba.fastjson2.JSON.toJSONString(persons);
    Persons persons2 = com.alibaba.fastjson2.JSON.parseObject(json, Persons.class);
    log.debug("Fast2Json Persons:{}", persons2);
  }

  @Benchmark
  public void testJackson() throws JsonProcessingException {
    String json = mapper.writeValueAsString(persons);
    Persons persons2 = mapper.readValue(json, Persons.class);
    log.debug("Jackson Persons:{}", persons2);
  }

  @Benchmark
  public void testGson() {
    String json = gson.toJson(persons);
    Person person2 = gson.fromJson(json, Person.class);
    log.debug("Gson Person:{}", person2);
  }

  @Test
  public void testBenchmark() throws Exception {
    Options options =
        new OptionsBuilder()
            .include(MoyenBench.class.getSimpleName())
            .forks(1)
            .threads(1)
            .warmupIterations(1)
            .measurementIterations(1)
            .mode(Mode.Throughput)
            .build();
    new Runner(options).run();
  }
}
