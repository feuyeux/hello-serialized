package org.feuyeux.jprotobuf.pojo;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
public class Persons {
  List<Person> persons;
  private Long id;
}
