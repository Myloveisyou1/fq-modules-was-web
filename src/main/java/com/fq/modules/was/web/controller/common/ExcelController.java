package com.fq.modules.was.web.controller.common;

import com.fq.modules.was.web.entity.datadictionary.DataDictionary;
import com.fq.modules.was.web.service.addresspool.AddressListService;
import com.fq.modules.was.web.service.datadictionary.DataDictionaryService;
import com.fq.modules.was.web.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/excel" )
public class ExcelController {

    private String path;

    @Autowired
    private DataDictionaryService service;
    @Autowired
    private AddressListService addressListService;

    /**
     * 导出数字货币管理模块的Excel
     *
     * @param wasDataDictionary
     * @return
     * @throws IOException
     */
    @RequestMapping("/toExcel" )
    public ResponseEntity<InputStreamResource> toExcel(DataDictionary wasDataDictionary) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("bean", wasDataDictionary);
        path = service.findAll(map);
        return downLoad();
    }

    /**
     * 导出地址池列表的Excel
     *
     * @param vo
     * @return
     * @throws IOException
     */
    @RequestMapping("/toExcelForAddressList" )
    public ResponseEntity<InputStreamResource> toExcelForAddressList(AddressVo vo) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("bean", vo);
        path = addressListService.findAll(map);
        return downLoad();
    }

    /**
     * 导出地址池明细列表的Excel
     *
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/toExcelForAddressDetails" )
    public ResponseEntity<InputStreamResource> toExcelForAddress(AddressVo vo) throws IOException, ParseException {

        Map<String, Object> map = new HashMap<>();
        map.put("bean", vo);
        path = addressListService.findAllDetails(map);
        return downLoad();
    }


    public ResponseEntity<InputStreamResource> downLoad() throws IOException {
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        if (fileSystemResource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate" );
            headers.add("Content-Disposition", String.format("attachment; filename=%s", fileSystemResource.getFilename()));
            headers.add("Pragma", "no-cache" );
            headers.add("Expires", "0" );
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(fileSystemResource.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream" ))
                    .body(new InputStreamResource(fileSystemResource.getInputStream()));
        }
        return null;
    }
}
