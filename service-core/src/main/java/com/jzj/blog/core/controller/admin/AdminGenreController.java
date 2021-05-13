package com.jzj.blog.core.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.service.GenreService;
import com.jzj.common.exception.Assert;
import com.jzj.common.result.R;
import com.jzj.common.result.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-04-27
 */
@Api(tags = "博客分类后台管理")
@RestController
@RequestMapping("/admin/core/genre")
@Slf4j
public class AdminGenreController {

    @Resource
    private GenreService genreService;

    @ApiOperation("博客分类列表")
    @GetMapping("/list/{page}/{limit}")
    public R list(@PathVariable Long page,@PathVariable Long limit){
        Page<Genre> pageParam = new Page<>(page, limit);
        IPage<Genre> listPage = genreService.listPage(pageParam);
        return R.ok().data("listPage",listPage);
    }
    @ApiOperation("增加博客分类")
    @PostMapping("/save")
    public R save(@RequestBody Genre genre){
        Assert.notNull(genre.getTypeName(),ResponseEnum.TYPE_NAME_NULL_ERROR); //判断分类名称是否为空
        QueryWrapper<Genre> wrapper = new QueryWrapper<>();
        int count = genreService.count(wrapper);
        Assert.isTrue(count<=0,ResponseEnum.TYPE_NAME__ERROR); //判断是否已存在改分类
        boolean result = genreService.save(genre);
        if(result){

            return R.ok().message("保存成功");
        }
        return R.error().message("保存失败");
    }
    @ApiOperation("修改博客分类")
    @PutMapping("/update")
    public R updateById(@RequestBody Genre genre){
        boolean result = genreService.updateById(genre);
        if(result){
            return  R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
    @ApiOperation("删除博客分类")
    @DeleteMapping("/remove/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = genreService.removeById(id);
        if(result){
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }

    @ApiOperation("根据ID查询博客分类")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable Long id){
        Genre model = genreService.getById(id);
        if(model!=null){
            return R.ok().data("model",model);
        }
        return R.error().message("该条记录不存在");
    }
}

