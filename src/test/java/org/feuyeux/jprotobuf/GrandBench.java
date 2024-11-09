package org.feuyeux.jprotobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.jprotobuf.pojo.Article;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@State(Scope.Thread)
@Slf4j
public class GrandBench {
  private Article article;
  private ObjectMapper mapper;
  private Gson gson;
  private Codec<Article> codec;

  @Setup
  public void setup() throws IOException {
    article=new Article();
    article.setId(10000L);
    article.setTenantId(10000L);
    article.setAuthor("TOM");
    article.setPublishTime(new Date());
    article.setTitle("1234567890");
    article.setSubTitle("qwertyuiopasdfghjklzxcvbnm");
    article.setHtmlContent(new String(Files.readAllBytes(Paths.get("src/test/resources/article.html"))));

    mapper = new ObjectMapper();
    gson = new Gson();
    codec = ProtobufProxy.create(Article.class);
  }

  @TearDown
  public void tearDown() {
    article = null;
    mapper = null;
    gson = null;
    codec = null;
  }

  @Benchmark
  public void testJProtobuf() throws IOException {
    byte[] bytes = codec.encode(article);
    Article article2 = codec.decode(bytes);
    log.debug("JProtobuf Article:{}", article2);
  }

  @Benchmark
  public void testFastJson() {
    String json = com.alibaba.fastjson.JSON.toJSONString(article);
    Article article2 = com.alibaba.fastjson.JSON.parseObject(json, Article.class);
    log.debug("FastJson Article:{}", article2);
  }

  @Benchmark
  public void testFast2Json() {
    String json = com.alibaba.fastjson2.JSON.toJSONString(article);
    Article article2 = com.alibaba.fastjson2.JSON.parseObject(json, Article.class);
    log.debug("Fast2Json Article:{}", article2);
  }

  @Benchmark
  public void testJackson() throws JsonProcessingException {
    String json = mapper.writeValueAsString(article);
    Article article2 = mapper.readValue(json, Article.class);
    log.debug("Jackson Article:{}", article2);
  }

  @Benchmark
  public void testGson() {
    String json = gson.toJson(article);
    Article article2 = gson.fromJson(json, Article.class);
    log.debug("Gson Article:{}", article2);
  }

  @Test
  public void testBenchmark() throws Exception {
    Options options =
        new OptionsBuilder()
            .include(GrandBench.class.getSimpleName())
            .forks(1)
            .threads(1)
            .warmupIterations(1)
            .measurementIterations(1)
            .mode(Mode.Throughput)
            .build();
    new Runner(options).run();
  }
}
