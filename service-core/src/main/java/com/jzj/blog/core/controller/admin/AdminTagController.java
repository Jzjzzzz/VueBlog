package com.jzj.blog.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.blog.core.pojo.entity.Dict;
import com.jzj.blog.core.pojo.entity.Tag;
import com.jzj.blog.core.pojo.query.TagQuery;
import com.jzj.blog.core.service.TagService;
import com.jzj.blog.core.util.SystemDictUtil;
import com.jzj.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-05-14
 */
@Api(tags = "博客标签后台管理")
@RestController
@RequestMapping("/admin/core/tag")
@Slf4j
public class AdminTagController {
    @Resource
    private TagService tagService;

    @Resource
    private SystemDictUtil systemDictUtil;


    @ApiOperation("/字典数据")
    @GetMapping("/dictList")
    public R dict(){
        List<Dict> dictListByParentId = systemDictUtil.getDictListByParentId();
        return R.ok().data("dict",dictListByParentId);
    }

    @ApiOperation("博客标签列表")
    @GetMapping("/list/{page}/{limit}")
    public R list(@PathVariable Long page, @PathVariable Long limit, TagQuery tagQuery){
        Page<Tag> pageParam = new Page<>(page, limit);
        IPage<Tag> listPage = tagService.listPage(pageParam,tagQuery);

        return R.ok().data("listPage",listPage);
    }

    @ApiOperation("新增博客标签")
    @PostMapping("/save")
    public R save(@RequestBody Tag tag){
        //设置初始值
        tag.setClickcount(0);
        boolean result = tagService.save(tag);
        if(result){
            return R.ok().message("新增成功");
        }
        return R.error().message("添加失败");
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
    @ApiOperation("根据ID查询博客标签")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Tag model = tagService.getById(id);
        if(model!=null){
            return R.ok().data("model",model);
        }
        return R.error().message("数据不存在");
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

    @ApiOperation("根据id置顶博客标签")
    @GetMapping("/stickyBlogById/{id}")
    public R stickyBlogById(@PathVariable Long id){
        boolean result = tagService.topBlogById(id);
        if(result){
            return R.ok().message("操作成功");

        }
        return R.error().message("操作失败");
    }
    @ApiOperation("批量删除标签")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Tag> tagList){
        boolean result = tagService.deleteBatchTag(tagList);
        if(result){
            return R.ok().message("批量删除成功");
        }
        return R.error().message("批量删除失败");
    }
}

