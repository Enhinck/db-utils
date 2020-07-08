package com.greentown.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.greentown.demo.model.vo.DemoVO;
import com.greentown.common.response.WebResponseEntity;
import io.swagger.annotations.*;
import com.greentown.demo.service.DemoService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

/**
 * 案例
 *
 * @author enhinck Generate auto created
 * @date 2020-07-07 19:33:38
 */
@Slf4j
@Api(description = "案例管理", tags = {"DemoController"})
@RestController
@AllArgsConstructor
public class DemoController  {
    private final DemoService demoService;

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("列表")
    @GetMapping("/demos")
    public WebResponseEntity<List<DemoVO>> list() {
        return WebResponseEntity.ok(demoService.listVO());
    }

    /**
     * 分页
     *
     * @param page 分页查询条件
     * @return
     */
    @ApiOperation("分页")
    @GetMapping("/demo/page")
    public WebResponseEntity<Page<DemoVO>> page(Page<DemoVO> page) {
        return WebResponseEntity.ok(demoService.pageVO(page));
    }

    /**
     * 新增
     *
     * @param entityVO 新增对象
     * @return
     */
    @ApiOperation("新增")
    @PostMapping("/demo")
    public WebResponseEntity<Boolean> add(@RequestBody DemoVO entityVO) {
        return WebResponseEntity.ok(demoService.saveVO(entityVO));
    }

    /**
     * 新增
     *
     * @param entityVO 
     * @return
     */
    @ApiOperation("新增")
    @PutMapping("/demo")
    public WebResponseEntity<Boolean> update(@RequestBody DemoVO entityVO) {
        return WebResponseEntity.ok(demoService.updateVOById(entityVO));
    }

    /**
     * 查询
     *
     * @param id 
     * @return
     */
    @ApiOperation("查询")
    @GetMapping("/demo/{id}")
    public WebResponseEntity<DemoVO> get(@PathVariable("id") Long id) {
        return WebResponseEntity.ok(demoService.getVOById(id));
    }

    /**
     * 删除
     *
     * @param id 
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("/demo/{id}")
    public WebResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        return WebResponseEntity.ok(demoService.removeById(id));
    }
}