package ru.sibsutis.shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sibsutis.shop.core.model.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
}