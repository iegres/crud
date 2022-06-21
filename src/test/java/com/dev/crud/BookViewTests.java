package com.dev.crud;

import com.dev.crud.entities.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookViewTests {

    @Autowired
    private BookView bookView;

    @Test
    public void gridShownWhenBookSelected() {
		// System.out.print("Testing book view...");
		// System.out.print(bookView);
		// Grid<Book> grid = bookView.grid;
		// Book firstBook = getFirstItem(grid);

		// assertFalse(grid.isVisible());
		// grid.asSingleSelect().setValue(firstBook);
		// assertTrue(grid.isVisible());
    }

	// private Book getFirstItem(Grid<Book> grid) {
	// 	return ((ListDataProvider<Book>) grid.getDataProvider()).getItems().iterator().next();
	// }
}
