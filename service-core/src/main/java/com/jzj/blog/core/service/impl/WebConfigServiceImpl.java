package com.jzj.blog.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.blog.base.global.SQLConf;
import com.jzj.blog.core.mapper.WebConfigMapper;
import com.jzj.blog.core.pojo.entity.WebConfig;
import com.jzj.blog.core.service.WebConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-05-19
 */
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService {

    @Override
    public WebConfig getWebConfig() {
        QueryWrapper<WebConfig> webConfigQueryWrapper = new QueryWrapper<>();
        webConfigQueryWrapper.eq(SQLConf.IS_DELETED,"0");
        WebConfig webConfig = baseMapper.selectOne(webConfigQueryWrapper);
        if(webConfig==null){
            WebConfig model= new WebConfig();
            model.setStatus(1);
            baseMapper.insert(model);
            return baseMapper.selectOne(webConfigQueryWrapper);
        }
        return webConfig;
    }

    @Override
    public boolean editWebConfig(WebConfig webConfig) {
        int sum = baseMapper.updateById(webConfig);
        if(sum>=1){
            return true;
        }
        return false;
    }
}
