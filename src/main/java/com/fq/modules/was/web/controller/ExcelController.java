package com.fq.modules.was.web.controller;

import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.entity.WasAddressList;
import com.fq.modules.was.web.entity.WasDataDictionary;
import com.fq.modules.was.web.service.WasDataDictionaryService;
import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.entity.WasAddressList;
import com.fq.modules.was.web.entity.WasDataDictionary;
import com.fq.modules.was.web.service.WasDataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/excel")
public class ExcelController {

    private String path ;

    @Autowired
    private WasDataDictionaryService service;

    @RequestMapping("/toExcel")
    public ResponseEntity<InputStreamResource> toExcel(WasDataDictionary wasDataDictionary) throws IOException {

        Map<String,Object> map = new HashMap<>();
        map.put("bean",wasDataDictionary);
       path = service.findAll(map);
        return downLoad();
    }

    /**
     * 导出地址池列表的Excel
     * @param wasAddressList
     * @return
     * @throws IOException
     */
    @RequestMapping("/toExcelForAddressList")
    public ResponseEntity<InputStreamResource> toExcelForAddressList(WasAddressList wasAddressList) throws IOException {

        Map<String,Object> map = new HashMap<>();
        map.put("bean",wasAddressList);
        path = service.findAll(map);
        return downLoad();
    }

    /**
     * 导出地址池明细列表的Excel
     * @param wasAddress
     * @return
     * @throws IOException
     */
    @RequestMapping("/toExcelForAddress")
    public ResponseEntity<InputStreamResource> toExcelForAddress(WasAddress wasAddress) throws IOException {

        Map<String,Object> map = new HashMap<>();
        map.put("bean",wasAddress);
        path = service.findAll(map);
        return downLoad();
    }


    public ResponseEntity<InputStreamResource> downLoad() throws IOException {
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        if (fileSystemResource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=%s", fileSystemResource.getFilename()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(fileSystemResource.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(fileSystemResource.getInputStream()));
        }
        return null;
    }
}
