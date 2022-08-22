package com.saehan.shop.service.posts;

import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.web.dto.ItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long save(@RequestBody ItemSaveRequestDto itemSaveRequestDto){
        return itemRepository.save(itemSaveRequestDto.toEntity()).getId();
    }


}
