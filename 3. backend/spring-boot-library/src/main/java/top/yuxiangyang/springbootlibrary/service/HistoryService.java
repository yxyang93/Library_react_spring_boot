package top.yuxiangyang.springbootlibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yuxiangyang.springbootlibrary.dao.HistoryRepository;
import top.yuxiangyang.springbootlibrary.dto.HistoryDTO;
import top.yuxiangyang.springbootlibrary.entity.History;

@Service
@Transactional
public class HistoryService {

    private HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public Page<HistoryDTO> getHistoryByUserEmail(String userEmail, Pageable pageable) {
        Page<History> histories = historyRepository.findBooksByUserEmail(userEmail, pageable);
        return histories.map(history -> new HistoryDTO(
                history.getId(),
                history.getUserEmail(),
                history.getCheckoutDate(),
                history.getReturnedDate(),
                history.getBook().getTitle(),
                history.getBook().getAuthor(),
                history.getBook().getDescription(),
                history.getBook().getImg()
        ));
    }
}
