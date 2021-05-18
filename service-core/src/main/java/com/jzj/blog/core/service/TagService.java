package com.jzj.blog.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.blog.core.pojo.entity.Tag;
import com.jzj.blog.core.pojo.query.TagQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-05-14
 */
public interface TagService extends IService<Tag> {

    IPage<Tag> listPage(Page<Tag> pageParam, TagQuery tagQuery);

    boolean topBlogById(Long id);

    boolean deleteBatchTag(List<Tag> tagList);
}
