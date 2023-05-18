package top.yuxiangyang.springbootlibrary.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import top.yuxiangyang.springbootlibrary.dto.HistoryDTO;
import top.yuxiangyang.springbootlibrary.service.HistoryService;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/histories")
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/{userEmail}")
    public Page<HistoryDTO> getHistoryByUserEmail(@PathVariable("userEmail") String userEmail,
                                                  @PageableDefault(size = 10) Pageable pageable) {
        return historyService.getHistoryByUserEmail(userEmail, pageable);
    }

}
