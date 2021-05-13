package com.jzj.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.blog.core.pojo.entity.Dict;

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
}
