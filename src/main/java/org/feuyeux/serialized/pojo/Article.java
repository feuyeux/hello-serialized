package org.feuyeux.serialized.pojo;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.util.Date;

@Data
@ProtobufClass
public class Article {
    private Long id;
    private String author;
    private Long tenantId;
    private String title;
    private String subTitle;
    private String htmlContent;
    private Date publishTime;
}