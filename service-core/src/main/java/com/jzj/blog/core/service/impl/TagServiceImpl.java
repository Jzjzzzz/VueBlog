package com.jzj.blog.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.core.mapper.TagMapper;
import com.jzj.blog.core.pojo.entity.Tag;
import com.jzj.blog.core.service.TagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-04-29
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Override
    public IPage<Tag> listPage(Page<Tag> pageParam) {
        return baseMapper.selectPage(pageParam,null);
    }
}
