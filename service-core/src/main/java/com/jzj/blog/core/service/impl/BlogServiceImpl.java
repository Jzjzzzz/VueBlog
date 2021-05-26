package com.jzj.blog.core.service.impl;

import com.jzj.blog.core.pojo.entity.Blog;
import com.jzj.blog.core.mapper.BlogMapper;
import com.jzj.blog.core.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-05-20
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
