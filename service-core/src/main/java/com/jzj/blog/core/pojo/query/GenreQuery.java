package com.jzj.blog.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="分类搜索对象")

public class GenreQuery {
    @ApiModelProperty(value = "分类内容")
    private String content;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
