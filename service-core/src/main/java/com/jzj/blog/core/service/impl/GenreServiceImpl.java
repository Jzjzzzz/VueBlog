package com.jzj.blog.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.core.mapper.GenreMapper;
import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.service.GenreService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-04-27
 */
@Service
public class GenreServiceImpl extends ServiceImpl<GenreMapper, Genre> implements GenreService {

    @Override
    public IPage<Genre> listPage(Page<Genre> pageParam) {
        return baseMapper.selectPage(pageParam,null);
    }
}
