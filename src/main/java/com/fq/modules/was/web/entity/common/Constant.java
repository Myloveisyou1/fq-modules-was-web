package com.fq.modules.was.web.entity.common;

/**
 * 常量
 */
public class Constant {

    /**
     * 导出数字货币管理界面的Excel
     **/
    public static final String URL_DICTIONARY = "http://localhost:8038/excel/toExcel";
    /**
     * 导出地址池管理界面的Excel
     **/
    public static final String URL_ADDRESS_LIST = "http://localhost:8038/excel/toExcelForAddressList";
    /**
     * 导出地址池详细界面的Excel
     **/
    public static final Object URL_ADDRESS_POOL_DETAILS = "http://localhost:8038/excel/toExcelForAddressDetails";

    public static final String URL_DATA_DICTIONARY = "http://10.45.0.54:8765/was/v1/wasAddressApi/general/dataDictionary";

    public static final String MIN_EARLY_WARNING = "剩余预警值";
}
