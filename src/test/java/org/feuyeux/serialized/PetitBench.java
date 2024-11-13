package org.feuyeux.serialized;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.feuyeux.serialized.pojo.Person;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;

@State(Scope.Thread)
@Slf4j
public class PetitBench {
  private Person person;
  private ObjectMapper mapper;
  private Gson gson;
  private Codec<Person> codec;
  private Fury fury;

  @Setup
  public void setup() {
    person = new Person();
    person.setId(1L);
    person.setName("TOM");
    person.setAge(29);

    mapper = new ObjectMapper();
    gson = new Gson();
    codec = ProtobufProxy.create(Person.class);
    fury = Fury.builder().withLanguage(Language.JAVA)
            .requireClassRegistration(true)
            .build();
    fury.register(Person.class);
  }

  @TearDown
  public void tearDown() {
    person = null;
    mapper = null;
    gson = null;
  }

  @Benchmark
  public void testJProtobuf() throws IOException {
    byte[] bytes = codec.encode(person);
    Person person2 = codec.decode(bytes);
    log.debug("JProtobuf Person:{}", person2);
  }

  @Benchmark
  public void testFury() throws IOException {
    byte[] bytes = fury.serialize(person);
    Person person2 =  (Person)fury.deserialize(bytes);
    log.debug("Fury Person:{}", person2);
  }

  @Benchmark
  public void testFastJson() {
    String json = com.alibaba.fastjson.JSON.toJSONString(person);
    Person person2 = com.alibaba.fastjson.JSON.parseObject(json, Person.class);
    log.debug("FastJson Person:{}", person2);
  }

  @Benchmark
  public void testFast2Json() {
    String json = com.alibaba.fastjson2.JSON.toJSONString(person);
    Person person2 = com.alibaba.fastjson2.JSON.parseObject(json, Person.class);
    log.debug("Fast2Json Person:{}", person2);
  }

  @Benchmark
  public void testJackson() throws JsonProcessingException {
    String json = mapper.writeValueAsString(person);
    Person person2 = mapper.readValue(json, Person.class);
    log.debug("Jackson Person:{}", person2);
  }

  @Benchmark
  public void testGson() {
    String json = gson.toJson(person);
    Person person2 = gson.fromJson(json, Person.class);
    log.debug("Gson Person:{}", person2);
  }

  @Test
  public void testBenchmark() throws Exception {
    Options options =
        new OptionsBuilder()
            .include(PetitBench.class.getSimpleName())
            .forks(1)
            .threads(1)
            .warmupIterations(1)
            .measurementIterations(1)
            .mode(Mode.Throughput)
            .build();
    new Runner(options).run();
  }
}
