package com.github.chengheaven.technology.serviceimpl;

import android.support.v4.app.Fragment;

import com.github.chengheaven.componentservice.service.technology.TechnologyService;
import com.github.chengheaven.technology.view.technology.TechnologyFragment;

/**
 * @author Heaven・Cheng Created on 2017/9/26.
 */

public class TechnologyServiceImpl implements TechnologyService {

    @Override
    public Fragment getTechnologyFragment() {
        return TechnologyFragment.newInstance();
    }
}
