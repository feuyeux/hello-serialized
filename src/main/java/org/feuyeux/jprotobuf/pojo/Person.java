package org.feuyeux.jprotobuf.pojo;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import java.util.List;
import lombok.Data;

@Data
public class Person {
  @Protobuf(fieldType = FieldType.INT64, order = 1, required = true)
  private Long id;

  @Protobuf(fieldType = FieldType.STRING, order = 2, required = true)
  private String name;

  @Protobuf(fieldType = FieldType.INT32, order = 3, required = true)
  private int age;

  @Protobuf(fieldType = FieldType.INT64, order = 4, required = false)
  private List<Long> friendIds;
}
