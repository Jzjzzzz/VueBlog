package com.jzj.blog.core.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.base.global.SQLConf;
import com.jzj.blog.core.mapper.BlogMapper;
import com.jzj.blog.core.mapper.GenreMapper;
import com.jzj.blog.core.mapper.TagMapper;
import com.jzj.blog.core.pojo.entity.Blog;
import com.jzj.blog.core.pojo.entity.Genre;
import com.jzj.blog.core.pojo.entity.Tag;
import com.jzj.blog.core.pojo.query.BlogQuery;
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

    @Override
    public IPage<Blog> listPage(Page<Blog> blogPage, BlogQuery blogQuery) {
        if(blogQuery==null){
            return baseMapper.selectPage(blogPage, null);
        }
        String title = blogQuery.getTitle();
        String tagId = blogQuery.getTagId();
        String blogSortName = blogQuery.getBlogSortName();
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.like(StringUtils.isNotBlank(title),"title",title)
                        .like(StringUtils.isNotBlank(tagId),"tag_id",tagId)
                        .like(StringUtils.isNotBlank(blogSortName),"blog_sort_name",blogSortName);
        return baseMapper.selectPage(blogPage,blogQueryWrapper);
    }

    @Override
    public boolean saveBlog(Blog blog) {
        String tagId = blog.getTagId();
        StringBuilder sb = new StringBuilder();

        String tagName="";
        String[] tag = tagId.split(",");
        for (int i=0;i<tag.length;i++) {
            String content = tagMapper.selectById(tag[i]).getContent();
            if(i==tag.length-1){
                sb.append(content);
            }else {
                sb.append(content+",");

            }
        }
        blog.setTagName(sb.toString());
        int result = baseMapper.insert(blog);
        if(result>=1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateByIdBlog(Blog blog ) {
        String tagId = blog.getTagId();
        StringBuilder sb = new StringBuilder();
        String tagName="";
        String[] tag = tagId.split(",");
        for (int i=0;i<tag.length;i++) {
            String content = tagMapper.selectById(tag[i]).getContent();
            if(i==tag.length-1){
                sb.append(content);
            }else {
                sb.append(content+",");

            }
        }
        blog.setTagName(sb.toString());
        int result = baseMapper.updateById(blog);
        if(result>=1){
            return true;
        }
        return false;
    }
}
