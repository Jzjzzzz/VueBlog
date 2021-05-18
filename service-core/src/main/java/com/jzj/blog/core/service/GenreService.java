package com.jzj.blog.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.pojo.query.GenreQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-05-18
 */
public interface GenreService extends IService<Genre> {

    IPage<Genre> listPage(Page<Genre> pageParam, GenreQuery genreQuery);

    boolean topBlogById(Long id);

    boolean deleteBatchTag(List<Genre> genreList);

}
