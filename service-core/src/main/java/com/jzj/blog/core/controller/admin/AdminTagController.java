package com.jzj.blog.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.blog.core.pojo.entity.Tag;
import com.jzj.blog.core.service.TagService;
import com.jzj.common.exception.Assert;
import com.jzj.common.result.R;
import com.jzj.common.result.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-04-29
 */
@Api(tags = "博客标签后台管理")
@RestController
@RequestMapping("/admin/core/tag")
public class AdminTagController {
    @Resource
    private TagService tagService;


    @ApiOperation("博客标签列表")
    @GetMapping("/list/{page}/{limit}")
    public R list(@PathVariable Long page,@PathVariable Long limit){
        Page<Tag> pageParam = new Page<>(page, limit);
        IPage<Tag> listPage = tagService.listPage(pageParam);
        return R.ok().data("listPage",listPage);
    }

    @ApiOperation("根据ID获取博客标签")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Tag model = tagService.getById(id);
        if(model!=null){
            return R.ok().data("model",model);
        }
        return R.error().message("该条数据不存在");
    }

    @ApiOperation("新增博客标签")
    @PostMapping("/save")
    public R save(@RequestBody Tag tag){
        Assert.notNull(tag.getTagName(), ResponseEnum.Tag_NAME_NULL_ERROR);
        boolean result = tagService.save(tag);
        if(result){
            return R.ok().message("保存成功");
        }
        return R.error().message("保存失败");
    }

    @ApiOperation("修改博客标签")
    @PutMapping("/update")
    public R updateById(@RequestBody Tag tag){
        boolean result = tagService.updateById(tag);
        if(result){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }

    @ApiOperation("删除博客标签")
    @DeleteMapping("/remove/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = tagService.removeById(id);
        if(result){
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }
}

