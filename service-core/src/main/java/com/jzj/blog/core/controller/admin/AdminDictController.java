package com.jzj.blog.core.controller.admin;


import com.alibaba.excel.EasyExcel;
import com.jzj.blog.core.pojo.dto.ExcelDictDTO;
import com.jzj.blog.core.pojo.entity.Dict;
import com.jzj.blog.core.service.DictService;
import com.jzj.common.exception.BusinessException;
import com.jzj.common.result.R;
import com.jzj.common.result.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-04-28
 */
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/admin/core/dict")
@Slf4j
public class AdminDictController {
    @Resource
    private DictService dictService;

    @ApiOperation("Excel数据的批量导入")
    @PostMapping("/import")
    public R batchImport(@RequestParam("file") MultipartFile file){
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            dictService.importData(inputStream);
            return R.ok().message("数据字典数据批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR,e);
        }
    }
    @ApiOperation("Excel数据的导出")
    @GetMapping("/export")
    public void download(HttpServletResponse response) throws IOException {
        LocalDate now = LocalDate.now();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("mydict-"+now, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ExcelDictDTO.class).sheet("数据字典").doWrite(dictService.listDictData());
    }
    @ApiOperation("根据上级id获取子节点数据列表")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(@PathVariable Long parentId){
        List<Dict> dictList = dictService.listByParentId(parentId);
        return R.ok().data("list",dictList);
    }

    @ApiOperation("新增数据字典")
    @PostMapping("/save")
    public R save(@RequestBody Dict dict){
        boolean result = dictService.save(dict);
        if(result){
            return R.ok().message("新增成功");
        }
        return R.error().message("新增失败");
    }
    @ApiOperation("根据字典ID查询数据字典")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Dict model = dictService.getById(id);
        if(model!=null){
            return R.ok().data("model",model);
        }
        return R.error().message("该条数据不存在");
    }

    @ApiOperation("修改数据字典")
    @PutMapping("/update")
    public R update(@RequestBody Dict dict){
        boolean result = dictService.updateById(dict);
        if(result){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }

    @ApiOperation("根据ID删除数据字典")
    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = dictService.removeById(id);
        if(result){
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }
}

