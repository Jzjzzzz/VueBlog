package com.jzj.blog.core;

import com.jzj.blog.core.pojo.entity.Dict;
import com.jzj.blog.core.util.SystemDictUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    private SystemDictUtil systemDictUtil;
    @Test
    public void test01(){
        List<Dict> dictList = systemDictUtil.getDictListByParentId();
        System.out.println(dictList);
    }
    @Test
    public void test02(){
        boolean result = systemDictUtil.removeKey();
        System.out.println(result);
    }
}
