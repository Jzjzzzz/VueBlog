package com.jzj.blog.core.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新建博客数据")
public class BlogSaveVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "博客简介")
    private String summary;

    @ApiModelProperty(value = "博客分类id")
    private Long blogSortId;

    @ApiModelProperty(value = "博客分类名称")
    private String blogSortName;

    @ApiModelProperty(value = "标签id")
    private String tagId;

    @ApiModelProperty(value = "推荐等级(0:正常)")
    private Integer level;

    @ApiModelProperty(value = "标题图片id")
    private String fileId;

    @ApiModelProperty(value = "是否原创（0:不是 1：是）")
    @TableField("is_original")
    private Boolean original;

    @ApiModelProperty(value = "是否开启评论(0:否 1:是)")
    private Boolean openComment;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "文章出处")
    private String articlesPart;
}
