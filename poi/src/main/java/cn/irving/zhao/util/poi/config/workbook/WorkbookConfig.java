package cn.irving.zhao.util.poi.config.workbook;

import cn.irving.zhao.util.poi.config.sheet.OuterSheetConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * excel文件配置信息
 */
public class WorkbookConfig {

    private final List<OuterSheetConfig> outerSheetConfigs = new ArrayList<>();

    public WorkbookConfig addOuterSheetConfig(OuterSheetConfig outerSheetConfig) {
        outerSheetConfigs.add(outerSheetConfig);
        return this;
    }

    public List<OuterSheetConfig> getOuterSheetConfigs() {
        return outerSheetConfigs;
    }

}
