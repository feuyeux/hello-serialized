package org.feuyeux.serialized;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.serialized.pojo.Person;
import org.junit.jupiter.api.Test;

@Slf4j
public class JProtoTest {

  @Test
  public void test() throws IOException {
    Person bulldog = new Person();
    bulldog.setId(1L);
    bulldog.setName("Spike Bulldog");
    bulldog.setAge(30);

    Person cat = new Person();
    cat.setId(2L);
    cat.setName("Tom Cat");
    cat.setAge(25);

    Person mouse = new Person();
    mouse.setId(3L);
    mouse.setName("Jerry Mouse");
    mouse.setAge(28);

    bulldog.setFriendIds(Arrays.asList(2L, 3L)); // cat和mouse的ID
    cat.setFriendIds(Arrays.asList(1L)); // bulldog的ID
    mouse.setFriendIds(Arrays.asList(2L));

    log.info("bulldog: {}", bulldog);

    Codec<Person> codec = ProtobufProxy.create(Person.class);
    byte[] bytes = codec.encode(bulldog);
    log.info("bulldog bytes length: {}", bytes.length);
    Person bulldog2 = codec.decode(bytes);
    log.info("bulldog2: {}", bulldog2);
  }
}
