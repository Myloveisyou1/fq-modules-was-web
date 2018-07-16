package com.fq.modules.was.web.controller.common;

import com.fq.modules.was.web.entity.basic.SysCurrency;
import com.fq.modules.was.web.entity.common.Constant;
import com.fq.modules.was.web.entity.common.Constant;
import com.fq.modules.was.web.service.basic.SysCurrencyService;
import com.fq.modules.was.web.service.basic.SysSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private SysCurrencyService sysCurrencyService;
    @Autowired
    private SysSourceService sysSourceService;

    /**
     * 跳转到登陆页面
     * @return
     */
    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }

    /**
     * 跳转到桌面
     * @return
     */
    @GetMapping(value = "/welcome")
    public String welcome() {
        return "/welcome";
    }

    /**
     * 跳转到首页
     * @return
     */
    @GetMapping(value = "/home")
    public String home() {
        return "/home";
    }

    /**
     * 加载顶部
     * @return
     */
    @GetMapping(value = "/common/top")
    public String top() {
        return "/common/top";
    }

    /**
     * 加载左边
     * @return
     */
    @GetMapping(value = "/common/left")
    public String left() {
        return "/common/left";
    }

    /**
     * 加载底部
     * @return
     */
    @GetMapping(value = "/common/footer")
    public String footer() {
        return "/common/footer";
    }

    /**
     * 数字货币管理
     * @return
     */
    @GetMapping(value = "/number-coin")
    public String numberCoin(Model model) {
        model.addAttribute("url",Constant.URL_DICTIONARY);
        return "/number-coin";
    }

    @GetMapping(value = "/number-coin-add")
    public String numberCoinAdd() {
        return "/number-coin-add";
    }

    /**
     * 数字货币监控及预警
     * @return
     */
    @GetMapping(value = "/control-waring")
    public String controlWaring() {
        return "/control-waring";
    }

    /**
     * 地址池管理
     * @return
     */
    @GetMapping(value = "/address-pool")
    public String addressPool(Model model) {
        model.addAttribute("url",Constant.URL_ADDRESS_LIST);

        //获取币种和平台
        model.addAttribute("currencyList",sysCurrencyService.findAll());
        model.addAttribute("sourceList",sysSourceService.findAll());
        return "/addresspool/address-pool";
    }
    /**
     * 地址池管理
     * @return
     */
    @GetMapping(value = "/address-pool-details")
    public String addressPooldetail(Model model, HttpServletRequest request) {
        model.addAttribute("url",Constant.URL_ADDRESS_POOL_DETAILS);
        model.addAttribute("wasType",request.getParameter("wasType"));
        model.addAttribute("wasSource",request.getParameter("wasSource"));
        return "/addresspool/address-pool-details";
    }
    /**
     * 预警提醒规则配置
     * @return
     */
    @GetMapping(value = "/address-warning-rule")
    public String addressWarningRule() {
        return "/addresspool/address-warning-rule";
    }
    /**
     * 预警提醒历史记录
     * @return
     */
    @GetMapping(value = "/address-warning-history")
    public String addressWarningHistory() {
        return "/addresspool/address-warning-history";
    }

    /**
     * 充值到账监控
     * @return
     */
    @GetMapping(value = "/recharge-control")
    public String rechargeControl() {
        return "/recharge-control";
    }

    /**
     * 平台提币管理
     * @return
     */
    @GetMapping(value = "/withdraw-coin")
    public String withdrawCoin() {
        return "/withdraw-coin";
    }

    /**
     * 404
     * @return
     */
    @GetMapping(value = "/errorpage/404")
    public String notFound() {
        return "/errorpage/404";
    }

    /**
     * 500
     * @return
     */
    @GetMapping(value = "/errorpage/500")
    public String error() {
        return "/errorpage/500";
    }



    /**系统设置**/
    /**
     * 用户
     * @return
     */
    @GetMapping(value = "/user")
    public String user() {
        return "/setting/user";
    }

    @GetMapping(value = "/user-add")
    public String userAdd() {
        return "/setting/user-add";
    }

    @GetMapping(value = "/user-info")
    public String userInfo() {
        return "/user-info";
    }

    /**
     * 角色
     * @return
     */
    @GetMapping(value = "/role")
    public String role() {
        return "/setting/role";
    }

    @GetMapping(value = "/role-add")
    public String roleAdd() {
        return "/setting/role-add";
    }


    /**
     * 菜单
     * @return
     */
    @GetMapping(value = "/menu")
    public String menu() {
        return "/setting/menu";
    }

    /**
     * 图标
     * @return
     */
    @GetMapping(value = "/icon")
    public String icon() {
        return "/setting/icon";
    }
    /**
     * 代码生成器
     * @return
     */
    @GetMapping(value = "/generator")
    public String generator() {
        return "/generator";
    }
    /**
     * 日志列表
     * @return
     */
    @GetMapping(value = "/was-sys-log")
    public String log() {
        return "/setting/was-sys-log";
    }
    /**
     * 参数配置
     * @return
     */
    @GetMapping(value = "/was-sys-config")
    public String config() {
        return "/addresspool/was-sys-config";
    }
    /**
     * 币种管理
     * @return
     */
    @GetMapping(value = "/was-sys-currency")
    public String currency() {
        return "/basic/was-sys-currency";
    }
    /**
     * 业务平台管理
     * @return
     */
    @GetMapping(value = "/was-sys-source")
    public String source() {
        return "/basic/was-sys-source";
    }
}
