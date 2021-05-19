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
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="WebConfig对象", description="")
public class WebConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "logo(文件UID)")
    private String logo;

    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "网站介绍")
    private String summary;

    @ApiModelProperty(value = "网站关键字")
    private String keyword;

    @ApiModelProperty(value = "网站作者")
    private String author;

    @ApiModelProperty(value = "网站备案号")
    private Integer recordNum;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "网站标题")
    private String title;

    @ApiModelProperty(value = "支付宝收款码")
    private String aliPay;

    @ApiModelProperty(value = "微信收款码")
    private String weixinPay;

    @ApiModelProperty(value = "github地址")
    private String github;

    @ApiModelProperty(value = "码云地址")
    private String gitee;

    @ApiModelProperty(value = "qq号")
    private String qqNumber;

    @ApiModelProperty(value = "qq群")
    private String qqGroup;

    @ApiModelProperty(value = "微信号")
    private String weChat;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "显示的列表（用于控制邮箱、QQ、QQ群、Github、Gitee、微信是否显示在前端）")
    private String showList;

    @ApiModelProperty(value = "登录方式列表（用于控制前端登录方式，如账号密码,码云,Github,QQ,微信）")
    private String loginTypeList;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

}
