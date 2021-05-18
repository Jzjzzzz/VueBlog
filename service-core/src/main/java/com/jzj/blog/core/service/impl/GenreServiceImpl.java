package com.jzj.blog.core.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.base.global.SQLConf;
import com.jzj.blog.core.mapper.GenreMapper;
import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.pojo.query.GenreQuery;
import com.jzj.blog.core.service.GenreService;
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
 * @since 2021-05-18
 */
@Service
public class GenreServiceImpl extends ServiceImpl<GenreMapper, Genre> implements GenreService {

    @Override
    public IPage<Genre> listPage(Page<Genre> pageParam, GenreQuery genreQuery) {
        String content = genreQuery.getContent();
        Integer status = genreQuery.getStatus();

            QueryWrapper<Genre> genreQueryWrapper = new QueryWrapper<>();
        genreQueryWrapper.eq(SQLConf.IS_DELETED,0);
        if(genreQuery==null){
            return baseMapper.selectPage(pageParam, genreQueryWrapper);
        }
        genreQueryWrapper
                .like(StringUtils.isNotBlank(content),"content",content)
                .eq(status!=null,SQLConf.STATUS,status);
        return baseMapper.selectPage(pageParam,genreQueryWrapper);
    }

    @Override
    public boolean topBlogById(Long id) {
        QueryWrapper<Genre> genreQueryWrapper = new QueryWrapper<>();
        genreQueryWrapper.eq(SQLConf.IS_DELETED,0);
        List<Genre> genres = baseMapper.selectList(genreQueryWrapper);
        //获取需要置顶的标签
        Genre genre = genres.stream().filter(s -> s.getId().equals(id)).findFirst().get();
        //查询当前标签集合中的最大值
        Integer maxSort = genres.stream().max(Comparator.comparingInt(Genre::getSort)).get().getSort();
        //设置需要置顶的标签为最大值
        genre.setSort(maxSort+1);
        int sum = baseMapper.updateById(genre);
        if(sum>=1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBatchTag(List<Genre> genreList) {
        Assert.isTrue(genreList.size()>=1, ResponseEnum.TYPE_SumTag__ERROR);
        ArrayList<Long> ids=new ArrayList<>();
        for (Genre genre : genreList) {
            ids.add(genre.getId());
        }
        int result = baseMapper.deleteBatchIds(ids);
        if(result>=1){
            return  true;
        }
        return false;
    }
}
