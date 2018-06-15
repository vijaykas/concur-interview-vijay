package com.concur.interview.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concur.interview.dto.ItemDto;
import com.concur.interview.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "Items" })
@RestController
@RequestMapping("/items")
public class ItemRestController {

    @Autowired
    ItemService itemService;

    @ApiOperation(value = "Create Item", notes = "Create a new item")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createItem(
            @ApiParam(value = "item", required = true) @RequestBody ItemDto itemDto,
            HttpServletResponse httpServletResponse) {
        boolean created = itemService.createItem(itemDto);
        if (created) {
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get Items List", notes = "Retrieve a list of items")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ItemDto> getItems() {
        return itemService.getItems();
    }

}
