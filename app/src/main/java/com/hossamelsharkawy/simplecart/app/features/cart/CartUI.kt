package com.hossamelsharkawy.simplecart.app.features.cart

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.hossamelsharkawy.simplecart.app.ui.component.MyButton
import com.hossamelsharkawy.simplecart.app.ui.component.MyIconButton
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor


@Composable
fun MyIconButtonAdd(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    MyIconButton(
        modifier = modifier,
        imageVector = Icons.Outlined.Add,
        tint = MyColor.green,
        onClick = onClick
    )
}

@Composable
fun MyIconButtonMin(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    MyIconButton(
        modifier = modifier,
        //   imageVector = FontAwesomeIcons ,
        imageVector = Icons.Outlined.Clear,
        tint = MyColor.red,
        onClick = onClick
    )
}





@Composable
fun SelectQtyUI(
    onClick: (qty: Int) -> Unit,
) {

    val padding = Modifier.padding(1.dp)


    //MaterialRadioButtonGroupComponent()
    FlowRow {
        /*  MyBtnAddToCart("Add 1/2 Kg") {
              onClick(1)
          }*/
        MyBtnAddToCart("Add 1 Kg", padding) {
            onClick(1)
        }
        MyBtnAddToCart("Add 2 Kg", padding) {
            onClick(2)
        }
        MyBtnAddToCart("Add 3 Kg", padding) {
            onClick(3)
        }
    }

}

@Composable
fun MyBtnAddToCart(
    text: String = "Add To Cart",
    modifier: Modifier,
    onClick: () -> Unit,
) {
    MyButton(text = text, modifier = modifier, onClick = onClick)
}


