package com.jzj.blog.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.core.listener.ExcelDictDTOListener;
import com.jzj.blog.core.mapper.DictMapper;
import com.jzj.blog.core.pojo.dto.ExcelDictDTO;
import com.jzj.blog.core.pojo.entity.Dict;
import com.jzj.blog.core.pojo.query.DictQuery;
import com.jzj.blog.core.service.DictService;
import com.jzj.common.exception.Assert;
import com.jzj.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-04-28
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private RedisTemplate redisTemplate;


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);
        //创建ExcelDictDTO列表，将Dict列表转换成ExcelDictDTO列表
        ArrayList<ExcelDictDTO> excelDictDTOList=new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ExcelDictDTO excelDictDTO=new ExcelDictDTO();
            BeanUtils.copyProperties(dict,excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public IPage<Dict> listPage(Page<Dict> pageParam, DictQuery dictQuery) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id",1);
        if(dictQuery==null){
            return baseMapper.selectPage(pageParam,dictQueryWrapper);
        }
        String name = dictQuery.getName(); //查询条件：字典名称
        String dict_code = dictQuery.getDict_code();//查询条件：字典编码

        dictQueryWrapper.like(StringUtils.isNotBlank(name),"name",name)
                .like(StringUtils.isNotBlank(dict_code),"dict_code",dict_code);

        return baseMapper.selectPage(pageParam,dictQueryWrapper);
    }

    @Override
    public boolean removeByIdTop(Long id) {
        //判断当前节点是否有子节点，找到当前的dict下级有没有子节点
        boolean hasChildren = this.hasChildren(id);
        Assert.isTrue(!hasChildren, ResponseEnum.Dic_Top_NotNull);
        int result = baseMapper.deleteById(id);
        if(result>=1){
            return true;
        }
        return false;
    }

    @Override
    public boolean saveTop(Dict dict) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        //查询出当前最大的id
        dictQueryWrapper.eq("parent_id",1)
                .orderByDesc("id")
                .last("limit 1");
        Dict dictMax = baseMapper.selectOne(dictQueryWrapper);
        //向上给Id增加1000

        dict.setId(dictMax.getId()+1000L);
        log.info("id:"+dict.getId());
        dict.setParentId(1L);
        int result = baseMapper.insert(dict);
        if(result>=1){
            return true;
        }
        return false;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        //首先查询redis中是否存在数据列表
        try {
            List<Dict> dictList =(List<Dict>) redisTemplate.opsForValue().get("blog:core:dictList:" + parentId);
            if(dictList!=null){
                log.info("从redis中获取数据列表");
                return dictList;
            }
        } catch (Exception e) {
            log.error("redis服务器异常:"+ ExceptionUtils.getStackTrace(e));
        }
        log.info("从数据库中获取数据列表");
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id",parentId);
        List<Dict> dictList = baseMapper.selectList(dictQueryWrapper);
        //填充hasChildren字段
        dictList.forEach(dict -> {
            //判断当前节点是否有子节点，找到当前的dict下级有没有子节点
            boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });
        //将数据存入redis
        try {
            redisTemplate.opsForValue().set("blog:core:dictList:" + parentId,dictList,60, TimeUnit.MINUTES);
            log.info("将数据存入redis");
        } catch (Exception e) {
            log.error("redis服务器异常:"+ ExceptionUtils.getStackTrace(e));
        }
        return dictList;
    }

    /**
     * 判断当前id所在的节点下是否有子节点
     * @param parentId
     * @return
     */
    private boolean hasChildren(Long parentId){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id",parentId);
        Integer count = baseMapper.selectCount(dictQueryWrapper);
        if(count.intValue()>0){
            return  true;
        }
        return false;
    }
}
