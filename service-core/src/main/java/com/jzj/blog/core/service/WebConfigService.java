package com.jzj.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.blog.core.pojo.entity.WebConfig;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-05-19
 */
public interface WebConfigService extends IService<WebConfig> {

    WebConfig getWebConfig();

    boolean editWebConfig(WebConfig webConfig);
}
