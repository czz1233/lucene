package com.xywy.lucene.api;

import com.xywy.lucene.mapper.BaikeMapper;
import com.xywy.lucene.model.Baike;
import com.xywy.lucene.service.SearchService;
import com.xywy.lucene.util.IndexDataBase;
import com.xywy.lucene.util.MsgResponse;
import com.xywy.lucene.util.SearchDataBase;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {
    @Autowired private BaikeMapper baikeMapper;
    @Autowired private SearchService searchService;


    // TODO 从数据库中拉取数据来创建索引
    @GetMapping("/index")
    public String createIndex(@RequestParam(value = "limit", required = false)int limit,@RequestParam(value = "offset", required = false)int offset) {
        // 拉取数据
        List<Baike> baikes = baikeMapper.getAllBaike(limit,offset);
        searchService.write(baikes);
        return "成功";
    }

    /**
     * 搜索，实现高亮
     * @param q
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    // TODO 1 执行搜索
    @GetMapping("search/{q}")
    public List<Map> getSearchText(@PathVariable String q) throws Exception {
        List<Map> mapList = searchService.search(q);

        return mapList;
    }

    @GetMapping(value = "/search")
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("/search");
        return mv;
    }

   /* @GetMapping("/createIndex")
    public String createIndexs(int limit,int offset) {
        // 拉取数据
        List<Baike> baikes = baikeMapper.getAllBaike(limit,offset);
        Baike baike = new Baike();
        //获取字段
        for (int i = 0; i < baikes.size(); i++) {
            //获取每行数据
            baike = baikes.get(i);
            //创建Document对象
            Document doc = new Document();
            //获取每列数据
            Field id = new Field("id", baike.getId()+"", TextField.TYPE_STORED);
            Field title = new Field("title", baike.getTitle(), TextField.TYPE_STORED);
            Field summary = new Field("summary", baike.getSummary(), TextField.TYPE_STORED);
            //添加到Document中
            doc.add(id);
            doc.add(title);
            doc.add(summary);
            //调用，创建索引库
            searchService.ceshiWrite(doc);
        }
        return "成功";
    }*/


}
