package com.jzj.blog.core.util;

import com.jzj.blog.core.mapper.DictMapper;
import com.jzj.blog.core.pojo.entity.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 描述:系统数据字典工具类
 * @author Jzj
 * @create 2021/5/27
**/
@Slf4j
@Component
public class SystemDictUtil {
    @Resource
    private  RedisTemplate redisTemplate;
    @Resource
    private  DictMapper dictMapper;

    private static final Long PARENT_ID=200L;
    private static final String NAME="blog:core:dictTopList:";
    /**
     * 根据父节点获取所有子节点
     * @param parentId
     * @return
     */
    public List<Dict> getDictListByParentId(Long parentId){
        List<Dict> dictList=null;
        try {
            //判断redis中是否存在数据
            dictList =(List<Dict>) redisTemplate.opsForValue().get(NAME);
            if(dictList==null) {
                dictList = dictMapper.selectList(null);
                redisTemplate.opsForValue().set(NAME, dictList, 60, TimeUnit.MINUTES);
                log.info("从数据库中获取数据：" + dictList);
            }
            dictList = dictList.stream().filter(s -> s.getParentId().equals(parentId)).collect(Collectors.toList());
        }catch (Exception e){
            log.error("Dict从redis里获取数据异常："+e);
        }
        return dictList;
    }
    /**
     * 查询数据状态列表
     * @param
     * @return
     */
    public List<Dict> getDictListByParentId(){
        List<Dict> dictList=null;
        try {
            //判断redis中是否存在数据
            dictList =(List<Dict>) redisTemplate.opsForValue(). get(NAME);
            log.info("从redis中获取数据："+dictList);
            if(dictList==null) {
                dictList = dictMapper.selectList(null);
                redisTemplate.opsForValue().set(NAME, dictList, 60, TimeUnit.MINUTES);
                log.info("从数据库中获取数据：" + dictList);
            }
            dictList = dictList.stream().filter(s -> s.getParentId().equals(PARENT_ID)).collect(Collectors.toList());
        }catch (Exception e){
            log.error("Dict从redis里获取数据异常："+e);
        }
        return dictList;
    }

    /**
     * 根据字典id从redis中获取字典数据
     * @param id
     * @return
     */
    public  Dict getDictById(Long id){
        Dict dict =null;
        List<Dict> dictList=null;
        try {
            //判断redis中是否存在数据
            dictList =(List<Dict>) redisTemplate.opsForValue().get(NAME);
            if(dictList!=null){
                dict = dictList.stream().filter(s -> s.getId().equals(id)).findFirst().get();
                log.info("从redis中获取的数据："+dict);
            }
            //当redis为空时从数据库添加数据
            if(dict==null){
                dictList = dictMapper.selectList(null);
                //数据存入redis
                redisTemplate.opsForValue().set(NAME,dictList, 60,TimeUnit.MINUTES);
                dict = dictList.stream().filter(s -> s.getId().equals(id)).findFirst().get();
                log.info("从数据库中获取的数据："+dict);
            }
        }catch (Exception e){
            log.error("Dict从redis里获取数据异常："+e);
        }
        return dict;
    }

    /**
     * 获取字典表所有数据
     * @return
     */
    public List<Dict> getDictList(){
        List<Dict> dictList=null;
        try {
            //判断redis中是否存在数据
            dictList =(List<Dict>) redisTemplate.opsForValue().get(NAME);
            log.info("从redis中获取的数据："+dictList);
            //当redis为空时从数据库添加数据
            if(dictList==null){
                dictList = dictMapper.selectList(null);
                //数据存入redis
                redisTemplate.opsForValue().set(NAME,dictList, 60,TimeUnit.MINUTES);
                log.info("从数据库中获取的数据："+dictList);

            }

        }catch (Exception e){
            log.error("Dict从redis里获取数据异常："+e);
        }
        return dictList;
    }

    /**
     * 清空当前Redis缓存
     * @return
     */
    public boolean removeKey(){
        try {
            redisTemplate.delete(NAME);
            return true;
        }catch (Exception e){
            log.error("Redis清空缓存时出错："+e);
        }
        return false;
    }
}
