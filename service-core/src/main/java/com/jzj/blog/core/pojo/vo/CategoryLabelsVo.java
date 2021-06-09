package com.jzj.blog.core.pojo.vo;

import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.pojo.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description="分类，标签信息集合")
public class CategoryLabelsVo {
    @ApiModelProperty("标签集合")
    private List<Tag> tagList;
    @ApiModelProperty("分类集合")
    private List<Genre> genreList;
}
