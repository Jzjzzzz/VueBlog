package com.jzj.blog.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.blog.core.pojo.entity.Tag;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-04-29
 */
public interface TagService extends IService<Tag> {


    IPage<Tag> listPage(Page<Tag> pageParam);
}
