package com.concur.interview.service;

import java.util.Collection;

import com.concur.interview.dto.ItemDto;

public interface ItemService {

    public boolean createItem(ItemDto itemDto);

    public Collection<ItemDto> getItems();
}
