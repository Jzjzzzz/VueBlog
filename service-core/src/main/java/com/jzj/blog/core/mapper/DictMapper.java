package com.jzj.blog.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzj.blog.core.pojo.dto.ExcelDictDTO;
import com.jzj.blog.core.pojo.entity.Dict;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author Jzj
 * @since 2021-04-28
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDTO> list);
}
