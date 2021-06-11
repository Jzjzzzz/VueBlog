package com.jzj.blog.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.blog.core.pojo.entity.Blog;
import com.jzj.blog.core.pojo.query.BlogQuery;
import com.jzj.blog.core.pojo.vo.CategoryLabelsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-05-20
 */
public interface BlogService extends IService<Blog> {

    CategoryLabelsVo getCategoryLabels();

    IPage<Blog> listPage(Page<Blog> blogPage, BlogQuery blogQuery);

    boolean saveBlog(Blog blog);

    boolean updateByIdBlog(Blog blog);
}
