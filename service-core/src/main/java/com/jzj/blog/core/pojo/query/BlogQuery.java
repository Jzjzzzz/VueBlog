package com.jzj.blog.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "博客搜索对象")
@Data
public class BlogQuery {

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "博客分类名")
    private String blogSortName;

    @ApiModelProperty(value = "标签id")
    private String tagId;

}
