package cn.irving.zhao.util.poi2.inter;

import cn.irving.zhao.util.poi2.enums.WorkbookType;

/**
 * workbook 需继承的接口
 */
public interface IWorkbook {

    default WorkbookType getWorkbookType() {
        return WorkbookType.XLSX;
    }

}
