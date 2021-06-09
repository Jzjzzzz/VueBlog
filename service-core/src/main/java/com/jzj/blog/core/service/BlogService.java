package com.jzj.blog.core.service;

import com.jzj.blog.core.pojo.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
