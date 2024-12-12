package com.jlf.cloud.service.impl;

import com.jlf.cloud.entities.Pay;
import com.jlf.cloud.mapper.PayMapper;
import com.jlf.cloud.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {
    @Resource
    private PayMapper payMapper;
    @Override
    public int add(Pay pay) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public int update(Pay pay) {
        return 0;
    }

    @Override
    public Pay getById(Integer id) {
        return null;
    }

    @Override
    public List<Pay> getAll() {
        return List.of();
    }
}
