package com.jzj.blog.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.blog.core.pojo.entity.Dict;
import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.pojo.query.GenreQuery;
import com.jzj.blog.core.service.GenreService;
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
 * @since 2021-05-18
 */
@Api(tags = "博客分类后台管理")
@RestController
@RequestMapping("/admin/core/genre")
@Slf4j
public class AdminGenreController {
    @Resource
    private GenreService genreService;

    @Resource
    private SystemDictUtil systemDictUtil;

    @ApiOperation("/字典数据")
    @GetMapping("/dictList")
    public R dict(){
        List<Dict> dictListByParentId = systemDictUtil.getDictListByParentId();
        return R.ok().data("dict",dictListByParentId);
    }

    @ApiOperation("博客分类列表")
    @GetMapping("/list/{page}/{limit}")
    public R list(@PathVariable Long page, @PathVariable Long limit, GenreQuery genreQuery){
        Page<Genre> pageParam = new Page<>(page, limit);
        IPage<Genre> listPage = genreService.listPage(pageParam,genreQuery);

        return R.ok().data("listPage",listPage);
    }

    @ApiOperation("新增博客分类")
    @PostMapping("/save")
    public R save(@RequestBody Genre genre){
        //初始化数据
        genre.setClickcount(0);
        boolean result = genreService.save(genre);
        if(result){
            return R.ok().message("新增成功");
        }
        return R.error().message("添加失败");
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
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Genre model = genreService.getById(id);
        if(model!=null){
            return R.ok().data("model",model);
        }
        return R.error().message("数据不存在");
    }
    @ApiOperation("修改博客分类")
    @PutMapping("/update")
    public R updateById(@RequestBody Genre genre){
        boolean result = genreService.updateById(genre);
        if(result){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }

    @ApiOperation("根据id置顶博客分类")
    @GetMapping("/stickyBlogById/{id}")
    public R stickyBlogById(@PathVariable Long id){
        boolean result = genreService.topBlogById(id);
        if(result){
            return R.ok().message("操作成功");

        }
        return R.error().message("操作失败");
    }
    @ApiOperation("批量删除分类")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Genre> genreList){
        boolean result = genreService.deleteBatchTag(genreList);
        if(result){
            return R.ok().message("批量删除成功");
        }
        return R.error().message("批量删除失败");
    }
}

