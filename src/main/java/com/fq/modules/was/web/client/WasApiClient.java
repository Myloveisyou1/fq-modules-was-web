package com.fq.modules.was.web.client;

import com.fq.core.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * was-api
 *
 * @author Amanda
 * @ClassName: PieWasClient
 * @Description:
 * @date 2018年5月22日
 */
@FeignClient(name = "${fq.modules.was-api.serviceId}", fallback = WasApiClient.WasApiClientFallback.class)
public interface WasApiClient {

    /**
     * 离线签名，原始交易（披萨）
     *
     * @param str
     * @return
     */
    @PostMapping(value = "v1/wasAddressApi/pizza/originalTransfer" )
    Result originalTransferPizza(@RequestParam("str" ) String str);

    /**
     * 离线签名，原始交易（X网）
     *
     * @param str
     * @return
     */
    @PostMapping(value = "/x/network/originalTransfer" )
    Result originalTransferXnet(@RequestParam("str" ) String str);

    /**
     * 离线签名，原始交易（馅饼）
     *
     * @param str
     * @return
     */
    @PostMapping(value = "/pie/originalTransfer" )
    Result originalTransferPie(@RequestParam("str" ) String str);

    /**
     * 离线签名，原始交易（X网 Xbrick）
     *
     * @param str
     * @return
     */
    @PostMapping(value = "/xbrick/originalTransfer" )
    Result originalTransferXbrick(@RequestParam("str" ) String str);

    /**
     * 轮询到账回调pie，异常处理类
     */
    @Component
    class WasApiClientFallback implements WasApiClient {

        private static final Logger LOGGER = LoggerFactory.getLogger(WasApiClientFallback.class);


        @Override
        public Result originalTransferPizza(String str) {
            // 调用失败
            LOGGER.error("回调bita，传递参数data=" + str + "，发生异常" );
            return null;
        }

        @Override
        public Result originalTransferXnet(String str) {
            // 调用失败
            LOGGER.error("回调xnet，传递参数data=" + str + "，发生异常" );
            return null;
        }

        @Override
        public Result originalTransferPie(String str) {
            // 调用失败
            LOGGER.error("回调pie，传递参数data=" + str + "，发生异常" );
            return null;
        }

        @Override
        public Result originalTransferXbrick(String str) {
            // 调用失败
            LOGGER.error("回调Xbrick，传递参数data=" + str + "，发生异常" );
            return null;
        }
    }
}