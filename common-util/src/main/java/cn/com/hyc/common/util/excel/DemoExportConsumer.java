package cn.com.hyc.common.util.excel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

/**
 * @author: hyc
 * @time: 2020/12/9 17:13
 */
public class DemoExportConsumer implements EasyExcelConsumer<DemoData> {

    public List<DemoData> query() {

        List<DemoData> list = new ArrayList<>(1000);
        for (int i = 0; i < 5000; i++) {
            DemoData export = new DemoData();
            list.add(export);
        }
        return list;
    }

    @Override
    public void accept(ExcelWriter excelWriter) {

        long l = 0;
        String sheetName = "order-";
        WriteSheet writeSheet = getSheet(sheetName + (l / 300000));
        for (int i = 0; i < 1000; i++) {
            List<DemoData> query = query();
            l += query.size();
            if (l > 300000) {
                writeSheet = getSheet(sheetName + (l / 300000));
            }
            this.write(excelWriter, writeSheet, query);
            query.clear();
            query = null;
        }
    }
}
