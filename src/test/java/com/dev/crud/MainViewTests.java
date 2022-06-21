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
public class MainViewTests {

    @Autowired
    private MainView mainView;

    @Test
    public void gridShownWhenAuthorSelected() {
		// System.out.print("Testing main view...");
		// System.out.print(mainView);
		// Grid<Author> grid = mainView.grid;
		// Author firstAuthor = getFirstItem(grid);

		// assertFalse(grid.isVisible());
		// grid.asSingleSelect().setValue(firstAuthor);
		// assertTrue(grid.isVisible());
    }

	// private Author getFirstItem(Grid<Author> grid) {
	// 	return ((ListDataProvider<Author>) grid.getDataProvider()).getItems().iterator().next();
	// }
}
