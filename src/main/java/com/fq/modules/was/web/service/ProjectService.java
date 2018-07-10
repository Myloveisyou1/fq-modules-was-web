package com.fq.modules.was.web.service;

import com.fq.modules.was.web.entity.Project;

import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/6/1 0001 10:36
 */
public interface ProjectService {

    List<Project> findAllProject(String projectName);

    boolean updateProject(Project project);

    boolean deleteProject(Long gid);

    int addProject(String projectName, String nameEn);

}
