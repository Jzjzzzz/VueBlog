package com.jzj.blog.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.base.global.SQLConf;
import com.jzj.blog.core.mapper.BlogMapper;
import com.jzj.blog.core.mapper.GenreMapper;
import com.jzj.blog.core.mapper.TagMapper;
import com.jzj.blog.core.pojo.entity.Blog;
import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.pojo.entity.Tag;
import com.jzj.blog.core.pojo.vo.CategoryLabelsVo;
import com.jzj.blog.core.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private TagMapper tagMapper;
    @Resource
    private GenreMapper genreMapper;

    //上架状态
    private static final int STATUS=1;

    @Override
    public CategoryLabelsVo getCategoryLabels() {
        CategoryLabelsVo categoryLabelsVo = new CategoryLabelsVo();
        //获取标签信息
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq(SQLConf.STATUS,STATUS);
        List<Tag> tagList = tagMapper.selectList(tagQueryWrapper);
        categoryLabelsVo.setTagList(tagList);
        //获取分类信息
        QueryWrapper<Genre> genreQueryWrapper = new QueryWrapper<>();
        genreQueryWrapper.eq(SQLConf.STATUS,1);
        List<Genre> genreList = genreMapper.selectList(genreQueryWrapper);
        categoryLabelsVo.setGenreList(genreList);
        return categoryLabelsVo;
    }
}
