package com.jzj.blog.core.controller.admin;


import com.jzj.blog.core.pojo.entity.WebConfig;
import com.jzj.blog.core.service.WebConfigService;
import com.jzj.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-05-19
 */
@Api(tags = "网站设置后台管理")
@RestController
@RequestMapping("/admin/core/webConfig")
@Slf4j
public class AdminWebConfigController {

    @Resource
    private WebConfigService webConfigService;

    @ApiOperation("获取网站配置")
    @GetMapping("/getWebConfig")
    public R getWebConfig(){
        WebConfig webConfig = webConfigService.getWebConfig();
        return R.ok().data("webConfig",webConfig);
    }

    @ApiOperation("修改网站配置")
    @PutMapping("/editWebConfig")
    public R editWebConfig(@RequestBody WebConfig webConfig){
        boolean result = webConfigService.editWebConfig(webConfig);
        if(result){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }

}

