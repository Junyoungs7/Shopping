package com.saehan.shop.service.posts;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.web.dto.ItemSaveRequestDto;
import com.saehan.shop.web.dto.ItemSearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchRequestDto itemSearchRequestDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchRequestDto, pageable);
    }


}
