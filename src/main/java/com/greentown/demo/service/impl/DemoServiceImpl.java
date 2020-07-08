package com.greentown.demo.service.impl;

import com.greentown.demo.model.domain.DemoDO;
import com.greentown.mybatisplus.service.impl.ScServiceImpl;
import org.springframework.stereotype.Service;
import com.greentown.demo.model.vo.DemoVO;
import com.greentown.demo.model.dto.DemoDTO;
import com.greentown.demo.service.DemoService;
import com.greentown.demo.mapper.DemoMapper;
import com.greentown.mybatisplus.service.IScService;

/**
 * 案例
 *
 * @author enhinck Generate auto created
 * @date 2020-07-07 19:33:38
 */
@Service
public class DemoServiceImpl extends ScServiceImpl<DemoMapper, DemoDO, DemoDTO, DemoVO> implements DemoService {
}