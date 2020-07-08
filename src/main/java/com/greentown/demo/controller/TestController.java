package com.greentown.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.greentown.demo.model.vo.TestVO;
import com.greentown.common.response.WebResponseEntity;
import io.swagger.annotations.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import com.greentown.demo.service.TestService;

/**
 * 测试
 *
 * @author enhinck Generate auto created
 * @date 2020-07-07 19:33:38
 */
@Slf4j
@Api(description = "测试管理", tags = {"TestController"})
@RestController
@AllArgsConstructor
public class TestController  {
    private final TestService testService;

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("列表")
    @GetMapping("/tests")
    public WebResponseEntity<List<TestVO>> list() {
        return WebResponseEntity.ok(testService.listVO());
    }

    /**
     * 分页
     *
     * @param page 分页查询条件
     * @return
     */
    @ApiOperation("分页")
    @GetMapping("/test/page")
    public WebResponseEntity<Page<TestVO>> page(Page<TestVO> page) {
        return WebResponseEntity.ok(testService.pageVO(page));
    }

    /**
     * 新增
     *
     * @param entityVO 新增对象
     * @return
     */
    @ApiOperation("新增")
    @PostMapping("/test")
    public WebResponseEntity<Boolean> add(@RequestBody TestVO entityVO) {
        return WebResponseEntity.ok(testService.saveVO(entityVO));
    }

    /**
     * 新增
     *
     * @param entityVO 
     * @return
     */
    @ApiOperation("新增")
    @PutMapping("/test")
    public WebResponseEntity<Boolean> update(@RequestBody TestVO entityVO) {
        return WebResponseEntity.ok(testService.updateVOById(entityVO));
    }

    /**
     * 查询
     *
     * @param id 
     * @return
     */
    @ApiOperation("查询")
    @GetMapping("/test/{id}")
    public WebResponseEntity<TestVO> get(@PathVariable("id") Long id) {
        return WebResponseEntity.ok(testService.getVOById(id));
    }

    /**
     * 删除
     *
     * @param id 
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("/test/{id}")
    public WebResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        return WebResponseEntity.ok(testService.removeById(id));
    }
}