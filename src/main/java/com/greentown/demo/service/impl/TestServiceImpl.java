package com.greentown.demo.service.impl;

import com.greentown.demo.model.domain.TestDO;
import com.greentown.mybatisplus.service.impl.ScServiceImpl;
import org.springframework.stereotype.Service;
import com.greentown.demo.mapper.TestMapper;
import com.greentown.demo.model.vo.TestVO;
import com.greentown.demo.model.dto.TestDTO;
import com.greentown.mybatisplus.service.IScService;
import com.greentown.demo.service.TestService;

/**
 * 测试
 *
 * @author enhinck Generate auto created
 * @date 2020-07-07 19:33:38
 */
@Service
public class TestServiceImpl extends ScServiceImpl<TestMapper, TestDO, TestDTO, TestVO> implements TestService {
}