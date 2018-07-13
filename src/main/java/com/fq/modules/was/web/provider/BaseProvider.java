package com.fq.modules.was.web.provider;

import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.entity.datadictionary.WasDataDictionary;
import com.fq.modules.was.web.entity.common.Pages;

import java.util.Map;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/30 0030 11:46
 */
public class BaseProvider {


    /**=======================数字货币管理相关============================================*/
    public String pageQuery(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT" +
                " was_id wasId,was_base_currency wasBaseCurrency,was_type wasType," +
                "was_begin_block wasBeginBlock,was_block_num wasBlockNum,was_gate_way wasGateWay," +
                "was_token_address wasTokenAddress,was_min_confirm wasMinConfirm," +
                "was_status wasStatus,was_precision wasPrecision," +
                "was_zero_gas_price wasZeroGasPrice,was_zero_gas_limit wasZeroGasLimit," +
                "was_transfer_gas_price wasTransferGasLimit,was_transfer_gas_limit wasTransferGasLimit," +
                "was_remark wasRemark,was_create_time wasCreateTime,was_last_time wasLastTime," +
                " IFNULL(was_coin_introduce_url,'') wasCoinIntroduceUrl,IFNULL(was_block_browsers_url,'') wasBlockBrowsersUrl,was_spare wasSpare" +
                " FROM" +
                " was_data_dictionary" +
                " WHERE 1=1");

        WasDataDictionary bean = (WasDataDictionary)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        if (CommonUtil.isNotEmpty(bean.getWasBaseCurrency())) {
            sql.append(" AND was_base_currency = '"+bean.getWasBaseCurrency()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND was_id = "+bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasType())) {
            sql.append(" AND was_type = '"+bean.getWasType()+"'");
        }

        sql.append(" ORDER BY was_type").append(" LIMIT "+pages.getPageSize()*(pages.getPageNumber()-1)+","+pages.getPageSize());
        return sql.toString();
    }

    public String pageQueryCount(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("select count(0) from was_data_dictionary where 1=1");

        WasDataDictionary bean = (WasDataDictionary)params.get("bean");

        if (CommonUtil.isNotEmpty(bean.getWasBaseCurrency())) {
            sql.append(" AND was_base_currency = '"+bean.getWasBaseCurrency()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND was_id = "+bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasType())) {
            sql.append(" AND was_type = '"+bean.getWasType()+"'");
        }

        return sql.toString();
    }

    public String findAll(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT" +
                " was_id wasId,was_base_currency wasBaseCurrency,was_type wasType," +
                "was_begin_block wasBeginBlock,was_block_num wasBlockNum,was_gate_way wasGateWay," +
                "was_token_address wasTokenAddress,was_min_confirm wasMinConfirm," +
                "was_status wasStatus,was_precision wasPrecision," +
                "was_zero_gas_price wasZeroGasPrice,was_zero_gas_limit wasZeroGasLimit," +
                "was_transfer_gas_price wasTransferGasLimit,was_transfer_gas_limit wasTransferGasLimit," +
                "was_remark wasRemark,was_create_time wasCreateTime,was_last_time wasLastTime," +
                " IFNULL(was_coin_introduce_url,'') wasCoinIntroduceUrl,IFNULL(was_block_browsers_url,'') wasBlockBrowsersUrl" +
                " FROM" +
                " was_data_dictionary" +
                " WHERE 1=1");

        WasDataDictionary bean = (WasDataDictionary)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        if (CommonUtil.isNotEmpty(bean.getWasBaseCurrency())) {
            sql.append(" AND was_base_currency = '"+bean.getWasBaseCurrency()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND was_id = "+bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasType())) {
            sql.append(" AND was_type = '"+bean.getWasType()+"'");
        }

        sql.append(" ORDER BY was_type");
        return sql.toString();
    }

    /**
     * 用户相关操作===============================================
     * @param userName
     * @return
     */

    public String findAllUser(String userName) {

        StringBuffer sql = new StringBuffer("select gid,user_name userName,password,tel,email,role_id roleId,role_name roleName,create_time createTime,update_time updateTime,login_time loginTime,status,version from was_sys_user where 1=1");
        if (CommonUtil.isNotEmpty(userName)) {
            sql.append(" AND user_name like '%"+userName+"%'");
        }

        sql.append(" order by gid desc");

        return sql.toString();
    }



    /**
     * 日志相关操作===============================================
     * @param
     * @return
     */

    public String pagingQueryLogs(Integer start,Integer end,String startTime,String endTime) {

        StringBuffer sql = new StringBuffer("select gid,url,method,ip,class_method classMethod,args,start_time startTime,end_time endTime,time,rep_data repData from logs where 1=1");

        if (CommonUtil.isNotEmpty(startTime)) {
            sql.append(" AND start_time >= '"+startTime+" 00:00:00'");
        }
        if (CommonUtil.isNotEmpty(endTime)) {
            sql.append(" AND end_time <= '"+endTime+" 23:59:59'");
        }
        sql.append(" order by gid desc limit "+start+","+end);

        return sql.toString();
    }

    public String pagingQueryLogsCount(String startTime,String endTime) {

        StringBuffer sql = new StringBuffer("select count(gid) count from logs where 1=1");

        if (CommonUtil.isNotEmpty(startTime)) {
            sql.append(" AND start_time >= '"+startTime+" 00:00:00'");
        }
        if (CommonUtil.isNotEmpty(endTime)) {
            sql.append(" AND end_time <= '"+endTime+" 23:59:59'");
        }

        return sql.toString();
    }
}
