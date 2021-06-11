package com.jzj.blog.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.blog.core.pojo.entity.Blog;
import com.jzj.blog.core.pojo.entity.Dict;
import com.jzj.blog.core.pojo.query.BlogQuery;
import com.jzj.blog.core.pojo.vo.BlogDictVo;
import com.jzj.blog.core.pojo.vo.BlogSaveVo;
import com.jzj.blog.core.pojo.vo.CategoryLabelsVo;
import com.jzj.blog.core.service.BlogService;
import com.jzj.blog.core.util.SystemDictUtil;
import com.jzj.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-05-20
 */
@Api(tags = "博客后台管理")
@RestController
@RequestMapping("/admin/core/blog")
@Slf4j
public class AdminBlogController {

    @Resource
    private BlogService blogService;
    @Resource
    private SystemDictUtil systemDictUtil;


    @ApiOperation("博客列表")
    @GetMapping("/blogList/{page}/{limit}")
    public R blogList(@PathVariable Long page, @PathVariable Long limit, BlogQuery blogQuery){
        Page<Blog> blogPage = new Page<>(page,limit);
        IPage<Blog> listPage = blogService.listPage(blogPage,blogQuery);
        return R.ok().data("listPage",listPage);
    }

    @ApiOperation("获取分类，标签数据")
    @GetMapping("/getCategoryLabels")
    public R getCategoryLabels(){
        CategoryLabelsVo categoryLabelsVo = blogService.getCategoryLabels();
        return R.ok().data("categoryLabelsVo",categoryLabelsVo);
    }

    @ApiOperation("获取字典数据")
    @GetMapping("/dictList")
    public R dict(){
        BlogDictVo blogDictVo = new BlogDictVo();
        //推荐列表
        List<Dict> dictStarsList = systemDictUtil.getDictListByParentId(400L);
        blogDictVo.setStarsList(dictStarsList);
        //是否原创
        List<Dict> dictOrderList = systemDictUtil.getDictListByParentId(100L);
        blogDictVo.setOrderList(dictOrderList);
        //是否开启评论
        List<Dict> dictCommentList = systemDictUtil.getDictListByParentId(300L);
        blogDictVo.setCommentList(dictCommentList);
        //是否上架
        List<Dict> dictSysList = systemDictUtil.getDictListByParentId();
        blogDictVo.setDataList(dictSysList);
        return R.ok().data("dict",blogDictVo);
    }

    @ApiOperation("新增博客")
    @PostMapping("/save")
    public R saveBlog(@RequestBody BlogSaveVo blogSaveVo){
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogSaveVo,blog);
        boolean result = blogService.saveBlog(blog);
        if(result){
            return  R.ok().message("新增成功");
        }
        return R.error().message("新增失败");

    }

    @ApiOperation("根据id获取博客")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Blog blog = blogService.getById(id);
        if(blog!=null){
            return R.ok().data("blog",blog);
        }
        return R.error().message("数据不存在");
    }

    @ApiOperation("根据id删除博客")
    @DeleteMapping("/remove/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = blogService.removeById(id);
        if(result){
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }
    @ApiOperation("根据ID修改博客详情")
    @PutMapping("/update")
    public R updateById(@RequestBody BlogSaveVo blogSaveVo){
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogSaveVo,blog);
        boolean result = blogService.updateByIdBlog(blog);
        if(result){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
}

