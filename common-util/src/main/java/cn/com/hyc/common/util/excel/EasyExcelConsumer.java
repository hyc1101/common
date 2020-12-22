package cn.com.hyc.common.util.excel;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

@FunctionalInterface
public interface EasyExcelConsumer<T> {

    void accept(ExcelWriter excelWriter, String sheetName);

    default void write(ExcelWriter excelWriter, WriteSheet writeSheet, List< ? super T> list) {

        excelWriter.write(list, writeSheet);
    }

    default WriteSheet getSheet(String sheetName) {

        return EasyExcel.writerSheet(sheetName).build();
    }
}
