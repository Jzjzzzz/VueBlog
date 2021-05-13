package com.jzj.blog.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jzj.blog.core.mapper.DictMapper;
import com.jzj.blog.core.pojo.dto.ExcelDictDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@NoArgsConstructor
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO> {

    private DictMapper dictMapper;

    //数据列表
    private List<ExcelDictDTO> list=new ArrayList<>();

    //每隔5条记录批量存储一次数据
    private static final int BATCH_COUNT=5;

    public ExcelDictDTOListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(ExcelDictDTO data, AnalysisContext analysisContext) {
        //将数据存入数据列表
        list.add(data);
        if(list.size()>=BATCH_COUNT){
            saveData();
            list.clear();
        }
        log.info("解析了一条数据：{}",data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //当最终剩余的数据不足BATCH_COUNT时，我们最终一次存储剩余数据
        saveData();
        log.info("所有数据解析完成！");
    }
    private void saveData(){
        log.info("{} 条数据被存储到数据库.......",list.size());
        //调用mapper层的批量save方法
        dictMapper.insertBatch(list);
        log.info("{} 条数据存储成功.......",list.size());

    }
}
