package cn.com.hyc.common.util.excel;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: hyc
 * @time: 2020/11/18 14:27
 */
@Slf4j
public class EasyExcelUtil<T> {

    public static <T> void writeExcel(HttpServletResponse response, String fileName, String sheetName, List<T> list) {

        ExcelWriter excelWriter = null;
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(getOutputStream(response, fileName), list.get(0).getClass()).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            // 去调用写入,实际使用时根据数据传入
            excelWriter.write(list, writeSheet);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error in writing Excel，fileName：" + fileName, e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    public static <T> void writeExcel(HttpServletResponse response, String fileName, Class< ? super T> head,
                                      EasyExcelConsumer<T> consumer) throws Exception {

        writeExcel(response, fileName, fileName, head, consumer);
    }

    public static <T> void writeExcel(HttpServletResponse response, String fileName, String sheetName, Class< ? super T> head,
                                      EasyExcelConsumer<T> consumer) throws Exception {

        ExcelWriter excelWriter = null;
        try {
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(getOutputStream(response, fileName), head).build();
            // 这里注意 如果同一个sheet只要创建一次
//            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            // 利用函数，实际使用时根据数据库分页的总的页数来
            consumer.accept(excelWriter);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    public static OutputStream getOutputStream(HttpServletResponse response, String name) throws Exception {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        return response.getOutputStream();
    }

}
