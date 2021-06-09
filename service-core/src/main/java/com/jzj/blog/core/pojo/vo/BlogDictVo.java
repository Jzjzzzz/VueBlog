package com.jzj.blog.core.pojo.vo;


import com.jzj.blog.core.pojo.entity.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "博客字典数据")
public class BlogDictVo {

    @ApiModelProperty("推荐等级字典")
    private List<Dict> starsList;

    @ApiModelProperty("是否原创字典")
    private List<Dict> orderList;

    @ApiModelProperty("是否开启评论")
    private List<Dict> commentList;

    @ApiModelProperty("是否上架")
    private  List<Dict> dataList;
}
