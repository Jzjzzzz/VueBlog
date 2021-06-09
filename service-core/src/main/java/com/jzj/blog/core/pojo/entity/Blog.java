package com.jzj.blog.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jzj
 * @since 2021-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Blog对象", description="")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "博客简介")
    private String summary;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "标签id")
    private String tagId;

    @ApiModelProperty(value = "博客点击数")
    private Integer clickCount;

    @ApiModelProperty(value = "博客收藏数")
    private Integer collectCount;

    @ApiModelProperty(value = "标题图片id")
    private String fileId;

    @ApiModelProperty(value = "是否原创（0:不是 1：是）")
    @TableField("is_original")
    private Boolean original;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "文章出处")
    private String articlesPart;

    @ApiModelProperty(value = "博客分类id")
    private Long blogSortId;

    @ApiModelProperty(value = "推荐等级(0:正常)")
    private Boolean level;

    @ApiModelProperty(value = "是否发布：0：否，1：是")
    @TableField("is_publish")
    private Boolean publish;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "是否开启评论(0:否 1:是)")
    private Boolean openComment;

    @ApiModelProperty(value = "类型【0 博客， 1：推广】")
    private Boolean type;

    @ApiModelProperty(value = "外链【如果是推广，那么将跳转到外链】")
    private String outsideLink;


}
