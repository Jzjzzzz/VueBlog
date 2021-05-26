package com.jzj.blog.core;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.blog.core.mapper.DictMapper;
import com.jzj.blog.core.pojo.entity.Dict;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DictTest {
    @Resource
    private DictMapper dictMapper;
    @Test
    public void test01(){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        //查询出当前最大的id
        dictQueryWrapper.eq("parent_id",1)
                .orderByDesc("id")
                .last("limit 1");
        Dict dictMax = dictMapper.selectOne(dictQueryWrapper);
        //向上给Id增加1000
        log.info("dictMax.getId():"+dictMax.getId());
        Long sum = dictMax.getId()+ 1000L;
        log.info("sum:"+sum);


    }
}
