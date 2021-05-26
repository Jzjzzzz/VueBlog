package com.jzj.blog.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.blog.core.pojo.entity.Dict;
import com.jzj.blog.core.pojo.query.DictQuery;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-04-28
 */
public interface DictService extends IService<Dict> {
    void importData(InputStream inputStream);

    List<Dict> listByParentId(Long parentId);

    List listDictData();

    IPage<Dict> listPage(Page<Dict> pageParam, DictQuery dictQuery);

    boolean removeByIdTop(Long id);

    boolean saveTop(Dict dict);
}
