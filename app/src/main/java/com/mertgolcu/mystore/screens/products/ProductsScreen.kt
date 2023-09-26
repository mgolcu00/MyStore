package com.mertgolcu.mystore.screens.products

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */

val categories = listOf("test", "test2", "test3")
val products = listOf(
    ExampleProduct("test", "test"),
    ExampleProduct("test2", "test2"),
    ExampleProduct("test3", "test3"),
    ExampleProduct("test4", "test"),
    ExampleProduct("test5", "test"),
    ExampleProduct("test6", "test"),
    ExampleProduct("test7", "test"),
    ExampleProduct("test8", "test2"),
    ExampleProduct("test9", "test2"),
    ExampleProduct("test10", "test2"),
    ExampleProduct("test11", "test3"),
    ExampleProduct("test12", "test3"),
    ExampleProduct("test13", "test3"),
)

@Composable
fun ProductsScreen() {
    val currentCategory = remember { mutableStateOf(categories[0]) }
    Surface {
        Column {
            CategoryTabs(categories = categories, onCategorySelected = {
                currentCategory.value = it
            })
            ProductList(currentCategory = currentCategory.value, products = products)
        }
    }
}

@Composable
fun CategoryTabs(
    categories: List<String>,
    onCategorySelected: (String) -> Unit
) {
    val selectedTab = remember { mutableIntStateOf(0) }
    TabRow(selectedTabIndex = selectedTab.intValue) {
        categories.forEachIndexed { index, category ->
            CategoryTab(
                selectedTab = categories[selectedTab.intValue],
                category = category,
                onClick = {
                    if (selectedTab.intValue == index) return@CategoryTab
                    selectedTab.intValue = index
                    onCategorySelected(category)
                }
            )
        }
    }

}

@Composable
fun CategoryTab(selectedTab: String, category: String, onClick: (String) -> Unit) {
    Tab(selected = category == selectedTab,
        text = {
            Text(text = category)
        },
        onClick = {
            onClick(category)
        }
    )
}

data class ExampleProduct(val name: String, val category: String)

@Composable
fun ProductList(currentCategory: String, products: List<ExampleProduct>) {
    val filteredProducts = remember(currentCategory, products) {
        products.filter { it.category == currentCategory }
    }
    LazyColumn {
        items(filteredProducts.size) {
            ProductItem(product = filteredProducts[it])
        }
    }
}

@Composable
fun ProductItem(product: ExampleProduct) {
    Row {
        Column {
            Text(modifier = Modifier.padding(8.dp), text = product.name)
        }
    }
}

@Composable
@Preview
fun ProductsScreenPreview() {
    ProductsScreen()
}