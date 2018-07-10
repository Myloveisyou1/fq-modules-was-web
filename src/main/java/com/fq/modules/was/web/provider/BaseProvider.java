package com.fq.modules.was.web.provider;

import com.fq.modules.was.web.entity.WasSysLog;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.entity.WasAddressList;
import com.fq.modules.was.web.entity.WasDataDictionary;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DateUtils;
import com.fq.modules.was.web.utils.DatesUtils;

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

    /**===========================地址池相关======================================================**/
    /**主体信息**/
    public String pageQueryWasAddressLists(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT c.*, count(1) totalCount FROM (");
        sql.append("SELECT was_type wasType,was_source wasSource,was_expire_time wasExpireTime,was_remark wasRemark")
                .append(" FROM was_address as a")
                .append(" UNION ALL")
                .append(" SELECT was_type wasType,was_source wasSource,was_expire_time wasExpireTime,was_remark wasRemark")
                .append(" FROM was_address_list as b")
                .append(" ) AS c");
        WasAddressList bean = (WasAddressList)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(params.get("wasId"))) {
            sql.append("was_id = "+params.get("wasId").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasType"))) {
            sql.append("was_type = "+params.get("wasType").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasAddress"))) {
            sql.append("was_address = "+params.get("wasAddress").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasExpireTime"))) {
            sql.append("was_expire_time = "+params.get("wasExpireTime").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasSource"))) {
            sql.append("was_source = "+params.get("wasSource").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasRemark"))) {
            sql.append("was_remark = "+params.get("wasRemark").toString());
        }
        sql.append(" GROUP BY c.wasType,c.wasSource,c.wasExpireTime,c.wasRemark");
        sql.append(" LIMIT "+pages.getPageSize()*(pages.getPageNumber()-1)+","+pages.getPageSize());
        return sql.toString();
    }

    public String pageQueryWasAddressListCount(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT count(0) FROM(SELECT c.*, count(1) totalCount FROM(");
        sql.append("SELECT was_type wasType,was_source wasSource,was_expire_time wasExpireTime,was_remark wasRemark")
                .append(" FROM was_address as a")
                .append(" UNION ALL")
                .append(" SELECT was_type wasType,was_source wasSource,was_expire_time wasExpireTime,was_remark wasRemark")
                .append(" FROM was_address_list as b")
                .append(" ) AS c");

        WasAddressList bean = (WasAddressList)params.get("bean");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(params.get("wasId"))) {
            sql.append("was_id = "+params.get("wasId").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasType"))) {
            sql.append("was_type = "+params.get("wasType").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasAddress"))) {
            sql.append("was_address = "+params.get("wasAddress").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasExpireTime"))) {
            sql.append("was_expire_time = "+params.get("wasExpireTime").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasSource"))) {
            sql.append("was_source = "+params.get("wasSource").toString());
        }
        if (CommonUtil.isNotEmpty(params.get("wasRemark"))) {
            sql.append("was_remark = "+params.get("wasRemark").toString());
        }
        sql.append(" GROUP BY c.wasType,c.wasSource,c.wasExpireTime,c.wasRemark");
        sql.append(")as d");
        return sql.toString();
    }

    /**总数的详细信息**/
    public String queryPageTotalCount(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT * FROM (");
        sql.append("SELECT was_id wasId,was_type wasType,was_source wasSource,was_address wasAddress,was_expire_time wasExpireTime,was_create_time wasCreateTime,was_remark,'1' fromT from was_address a")
                .append(" UNION ALL")
                .append(" SELECT was_id wasId,was_type wasType,was_source wasSource,was_address wasAddress,was_expire_time wasExpireTime,was_create_time wasCreateTime,was_remark,'2' fromT from was_address_list b)c where 1=1");

        WasAddress bean = (WasAddress)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND c.wasId = "+bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasAddress())) {
            sql.append(" AND c.wasAddress like '%"+bean.getWasAddress()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasType())) {
            sql.append(" AND c.wasType = '"+bean.getWasType()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasSource())) {
            sql.append(" AND c.wasSource = '"+bean.getWasSource()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasExpireTime())) {
            sql.append(" AND c.wasExpireTime = "+bean.getWasExpireTime());
        }

        sql.append(" ORDER BY c.wasType,c.wasSource,c.wasExpireTime");
        sql.append(" LIMIT "+pages.getPageSize()*(pages.getPageNumber()-1)+","+pages.getPageSize());
        return sql.toString();
    }

    public String queryPageTotalCounts(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT count(1) FROM (");
        sql.append("SELECT was_id wasId,was_type wasType,was_source wasSource,was_address wasAddress,was_expire_time wasExpireTime,was_create_time wasCreateTime,was_remark,'1' fromT from was_address a")
                .append(" UNION ALL")
                .append(" SELECT was_id wasId,was_type wasType,was_source wasSource,was_address wasAddress,was_expire_time wasExpireTime,was_create_time wasCreateTime,was_remark,'2' fromT from was_address_list b)c where 1=1");

        WasAddress bean = (WasAddress)params.get("bean");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND c.wasId = "+bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasAddress())) {
            sql.append(" AND c.wasAddress like '%"+bean.getWasAddress()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasType())) {
            sql.append(" AND c.wasType = '"+bean.getWasType()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasSource())) {
            sql.append(" AND c.wasSource = '"+bean.getWasSource()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasExpireTime())) {
            sql.append(" AND c.wasExpireTime = "+bean.getWasExpireTime());
        }

        return sql.toString();
    }

    /**剩余数量的详细信息**/
    public String queryPageSurplusCount(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT was_id wasId,was_type wasType,was_source wasSource,was_address wasAddress,was_expire_time wasExpireTime,was_create_time wasCreateTime,was_remark,'2' fromT from was_address_list b where 1=1");

        WasAddress bean = (WasAddress)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND b.wasId = "+bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasAddress())) {
            sql.append(" AND b.wasAddress like '%"+bean.getWasAddress()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasType())) {
            sql.append(" AND b.was_type = '"+bean.getWasType()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasSource())) {
            sql.append(" AND b.was_source = '"+bean.getWasSource()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasExpireTime())) {
            sql.append(" AND b.was_expire_time = "+bean.getWasExpireTime());
        }

        sql.append(" ORDER BY b.was_type,b.was_source,b.was_expire_time");
        sql.append(" LIMIT "+pages.getPageSize()*(pages.getPageNumber()-1)+","+pages.getPageSize());
        return sql.toString();
    }

    public String queryPageSurplusCounts(Map<String,Object> params) {

        StringBuffer sql = new StringBuffer("SELECT count(1) from was_address_list b where 1=1");

        WasAddress bean = (WasAddress)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND b.wasId = "+bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasAddress())) {
            sql.append(" AND b.wasAddress like '%"+bean.getWasAddress()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasType())) {
            sql.append(" AND b.was_type = '"+bean.getWasType()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasSource())) {
            sql.append(" AND b.was_source = '"+bean.getWasSource()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasExpireTime())) {
            sql.append(" AND b.was_expire_time = "+bean.getWasExpireTime());
        }
        return sql.toString();
    }


    /**
     * 系统日志 相关=============================================
     */
    public String pageQueryWasSysLogList(Map<String, Object> params) {

        StringBuffer sql = new StringBuffer("select was_id wasId,was_log_type wasLogType,was_log_operator wasLogOperator,was_log_content wasLogContent,was_log_result wasLogResult,was_log_time wasLogTime from was_sys_log where 1=1");

        WasSysLog bean = (WasSysLog) params.get("bean");
        Pages pages = (Pages) params.get("pages");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND was_id = " + bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogType())) {
            sql.append(" AND was_log_type = " + bean.getWasLogType());
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogOperator())) {
            sql.append(" AND was_log_operator = '" + bean.getWasLogOperator()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogContent())) {
            sql.append(" AND was_log_content like '%" + bean.getWasLogContent()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogResult())) {
            sql.append(" AND was_log_result like '%" + bean.getWasLogResult()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getStartTime())) {
            if (CommonUtil.isEmpty(bean.getEndTime())) {
                sql.append(" AND was_log_time >= '"+bean.getStartTime()+" 00:00:00' AND was_log_time <= '"+DatesUtils.time() +"'");
            } else {
                sql.append(" AND was_log_time >= '"+bean.getStartTime()+" 00:00:00' AND was_log_time <= '"+bean.getEndTime() +" 23:59:59'");
            }
        }
        sql.append(" ORDER BY was_id DeSC");
        sql.append(" LIMIT " + pages.getPageSize() * (pages.getPageNumber() - 1) + "," + pages.getPageSize());
        return sql.toString();
    }

    public String pageQueryWasSysLogCount(Map<String, Object> params) {

        StringBuffer sql = new StringBuffer("select count(0) from was_sys_log where 1=1");

        WasSysLog bean = (WasSysLog) params.get("bean");

        //这里写查询条件
        if (CommonUtil.isNotEmpty(bean.getWasId())) {
            sql.append(" AND was_id = " + bean.getWasId());
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogType())) {
            sql.append(" AND was_log_type = " + bean.getWasLogType());
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogOperator())) {
            sql.append(" AND was_log_operator = '" + bean.getWasLogOperator()+"'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogContent())) {
            sql.append(" AND was_log_content like '%" + bean.getWasLogContent()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getWasLogResult())) {
            sql.append(" AND was_log_result like '%" + bean.getWasLogResult()+"%'");
        }
        if (CommonUtil.isNotEmpty(bean.getStartTime())) {
            if (CommonUtil.isEmpty(bean.getEndTime())) {
                sql.append(" AND was_log_time >= '"+bean.getStartTime()+"' AND was_log_time <= '"+DatesUtils.time() +"'");
            } else {
                sql.append(" AND was_log_time >= '"+bean.getStartTime()+"' AND was_log_time <= '"+bean.getEndTime() +"'");
            }
        }

        return sql.toString();
    }

    /**
     * 用户相关操作===============================================
     * @param userName
     * @return
     */

    public String findAllUser(String userName) {

        StringBuffer sql = new StringBuffer("select gid,user_name userName,password,tel,email,role_id roleId,role_name roleName,create_time createTime,update_time updateTime,login_time loginTime,status,version from sys_user where 1=1");
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
