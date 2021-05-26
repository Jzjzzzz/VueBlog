package com.jzj.blog.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="标签搜索对象")
public class TagQuery {
    @ApiModelProperty(value = "标签内容")
    private String content;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
