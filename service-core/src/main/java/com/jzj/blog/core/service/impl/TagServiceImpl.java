package com.jzj.blog.core.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.base.global.SQLConf;
import com.jzj.blog.core.mapper.TagMapper;
import com.jzj.blog.core.pojo.entity.Tag;
import com.jzj.blog.core.pojo.query.TagQuery;
import com.jzj.blog.core.service.TagService;
import com.jzj.common.exception.Assert;
import com.jzj.common.result.ResponseEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-05-14
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Override
    public IPage<Tag> listPage(Page<Tag> pageParam, TagQuery tagQuery) {
        String content = tagQuery.getContent();
        Integer status = tagQuery.getStatus();

        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq(SQLConf.IS_DELETED,0);
        if(tagQuery==null){
            return baseMapper.selectPage(pageParam, tagQueryWrapper);
        }
        tagQueryWrapper
                .like(StringUtils.isNotBlank(content),"content",content)
                .eq(status!=null,SQLConf.STATUS,status);
        return baseMapper.selectPage(pageParam,tagQueryWrapper);
    }

    @Override
    public boolean topBlogById(Long id) {
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq(SQLConf.IS_DELETED,0);
        List<Tag> tags = baseMapper.selectList(tagQueryWrapper);
        //获取需要置顶的标签
        Tag tag = tags.stream().filter(s -> s.getId().equals(id)).findFirst().get();
        //查询当前标签集合中的最大值
        Integer maxSort = tags.stream().max(Comparator.comparingInt(Tag::getSort)).get().getSort();
        //设置需要置顶的标签为最大值
        tag.setSort(maxSort+1);
        int sum = baseMapper.updateById(tag);
        if(sum>=1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBatchTag(List<Tag> tagList) {
        Assert.isTrue(tagList.size()>=1, ResponseEnum.Tag_SumTag__ERROR);
        ArrayList<Long> ids=new ArrayList<>();
        for (Tag tag : tagList) {
            ids.add(tag.getId());
        }
        int result = baseMapper.deleteBatchIds(ids);
        if(result>=1){
            return  true;
        }
        return false;
    }
}


