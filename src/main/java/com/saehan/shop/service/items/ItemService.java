package com.saehan.shop.service.items;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.item.ItemImg;
import com.saehan.shop.domain.item.ItemImgRepository;
import com.saehan.shop.domain.item.ItemRepository;
import com.saehan.shop.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto2 itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        Item item = itemFormDto.createItem();

        itemRepository.save(item);

        for(int i = 0; i < itemImgFileList.size(); i++){
            ItemImg itemImg;
            if(i == 0){
                itemImg = ItemImg.builder().item(item).repImgYn("Y").build();
            }
            else{
                itemImg = ItemImg.builder().item(item).repImgYn("N").build();
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto2 getItemDtl(Long itemId){
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for(ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto2 itemFormDto2 = ItemFormDto2.of(item);
        itemFormDto2.setItemImgDtoList(itemImgDtoList);

        return itemFormDto2;
    }

    public Long updateItem(ItemFormDto2 itemFormDto2, List<MultipartFile> itemImgFileList) throws Exception{

        Item item = itemRepository.findById(itemFormDto2.getId()).orElseThrow(EntityNotFoundException::new);
        item.update(itemFormDto2);
        List<Long> itemImgIds = itemFormDto2.getItemImgIds();

        for(int i = 0; i < itemImgFileList.size(); i++){
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional
    public Long save(@RequestBody ItemSaveRequestDto itemSaveRequestDto){
        return itemRepository.save(itemSaveRequestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchRequestDto itemSearchRequestDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchRequestDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchRequestDto itemSearchRequestDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchRequestDto, pageable);
    }

}
