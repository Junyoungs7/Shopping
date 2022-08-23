package com.saehan.shop.domain.item;

import com.saehan.shop.web.dto.ItemSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchRequestDto itemSearchRequestDto, Pageable pageable);
}
