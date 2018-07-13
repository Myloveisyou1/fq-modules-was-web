package com.fq.modules.was.web.controller.setting;

import com.fq.modules.was.web.entity.setting.Project;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.setting.ProjectService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descript: 项目配置
 * @Author: liuyingjie
 * @Date: create in 2018/6/1 0001 10:34
 */
@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    /**
     * 查询所有项目
     * @param projectName
     * @return
     */
    @GetMapping(value = "/findAllProject")
    public Result findAllProject(@RequestParam(value = "projectName",required = false) String projectName) {

        return ResultUtil.success(service.findAllProject(projectName),null);
    }

    /**
     * 修改信息
     * @param project
     * @return
     */
    @GetMapping(value = "/updateProject")
    public Result updateProject(Project project) {

        return ResultUtil.success(service.updateProject(project),null);
    }

    /**
     * 删除项目
     * @return
     */
    @GetMapping(value = "/deleteProject")
    public Result deleteProject(@RequestParam(value = "gid") Long gid) {

        return ResultUtil.success(service.deleteProject(gid),null);
    }

    /**
     * 添加项目
     * @return
     */
    @GetMapping(value = "/addProject")
    public Result addProject(@RequestParam(value = "projectName") String projectName, @RequestParam(value = "nameEn") String nameEn) {

        return ResultUtil.success(service.addProject(projectName,nameEn),null);
    }
}
