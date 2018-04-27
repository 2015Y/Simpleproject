package com.bootdo.test.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.common.annotation.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
//import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.test.domain.TestDO;
import com.bootdo.test.service.TestService;


/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-26 10:54:46
 */
@Controller
@RequestMapping("/blog/test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping()
    @RequiresPermissions("blog:test")
    String Test() {
        return "test/test/test";
    }

    @Log("请求测试数据")
    @ResponseBody
    @GetMapping("/list")
    //@RequiresPermissions("blog:list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<TestDO> testList = testService.list(query);
        int total = testService.count(query);
        PageUtils pageUtils = new PageUtils(testList, total);
        return pageUtils;
    }

    @Log("添加测试数据")
    @GetMapping("/add")
        //@RequiresPermissions("blog:bComments")
    String add() {
        return "blog/test/add";
    }

    @Log("编辑测试数据")
    @GetMapping("/edit")
        //@RequiresPermissions("blog:bComments")
    String edit(Integer testId, Model model) {
        TestDO test = testService.get(testId);
        model.addAttribute("Test", test);
        return "blog/test/edit";
    }

    /**
     * 信息
     */
    @Log("根据Id获取测试数据")
    @RequestMapping("/info/{testId}")
//	@RequiresPermissions("blog:info")
    public R info(@PathVariable("testId") Integer testId) {
        TestDO test = testService.get(testId);
        return R.ok().put("test", test);
    }

    /**
     * 保存
     */
    @Log("添加测试数据")
    @ResponseBody
    @PostMapping("/save")
//	@RequiresPermissions("blog:save")
    public R save(TestDO test) {
        if (testService.save(test) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @Log("修改测试数据")
    @ResponseBody()
    @PostMapping("/update")
    public R update(TestDO test) {
        testService.update(test);
        return R.ok();
    }


    /**
     * 删除
     */
    @Log("根据Id删除测试数据")
    @PostMapping("/remove")
    @ResponseBody
//@RequiresPermissions("blog:remove")
    public R remove(Integer testId) {
        if (testService.remove(testId) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @Log("根据Id批量删除测试数据")
    @PostMapping("/batchRemove")
    @ResponseBody
//@RequiresPermissions("blog:remove")
    public R remove(@RequestParam("ids[]") Integer[] testIds) {
        testService.batchRemove(testIds);

        return R.ok();
    }

}
