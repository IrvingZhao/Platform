package cn.irving.zhao.util.poi.enums;

/**
 * 工作表类型
 */
public enum SheetType {
    /**
     * 内嵌工作表
     */
    INNER,
    /**
     * 新工作表
     */
    OUTER;
//    /**
//     * 默认表格，在{@link IWorkbook}类中的带有{@link Cell}配置 或 {@link Sheet#type()}为{@link SheetType#INNER} 的属性 会进入默认工作表中
//     * <p>默认工作表在所有工作表之前</p>
//     */
//    DEFAULT;
}
